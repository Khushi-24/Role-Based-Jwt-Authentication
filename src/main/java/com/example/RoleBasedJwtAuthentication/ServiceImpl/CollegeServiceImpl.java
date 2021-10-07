package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDepartmentDto;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDto;
import com.example.RoleBasedJwtAuthentication.Dto.UniversityDto;
import com.example.RoleBasedJwtAuthentication.Dto.ZoneDto;
import com.example.RoleBasedJwtAuthentication.Entity.College;
import com.example.RoleBasedJwtAuthentication.Entity.CollegeDepartment;
import com.example.RoleBasedJwtAuthentication.Entity.Department;
import com.example.RoleBasedJwtAuthentication.HelperClass;
import com.example.RoleBasedJwtAuthentication.Repository.*;
import com.example.RoleBasedJwtAuthentication.Service.CollegeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollegeServiceImpl implements CollegeService {

    private final CollegeRepository collegeRepository;

    private final PrincipalRepository principalRepository;

    private final DepartmentRepository departmentRepository;

    private final StudentRepository studentRepository;

    private final CollegeDepartmentRepository collegeDepartmentRepository;

    private final UniversityRepository universityRepository;

    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public CollegeDto addCollege(CollegeDto collegeDto) {
        if(!collegeRepository.existsById(collegeDto.getCollegeId())){
            if(universityRepository.existsById(collegeDto.getUniversity().getUniversityId())){
                College college = new College();
                modelMapper.map(collegeDto,college);
                collegeRepository.save(college);
                return collegeDto;
            }else{
                throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "Please enter a valid university code, university doesn't exists.");
            }
        }else {
            throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "College already exists with college Id " + collegeDto.getCollegeId() +
                    ".");
        }

    }

    @Override
    public void addDepartmentToCollege(CollegeDepartmentDto collegeDepartmentDto) {

        College college = collegeRepository.findById(collegeDepartmentDto.getCollegeId()).orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND, "College doesn't exists."));
        Department department = departmentRepository.findById(collegeDepartmentDto.getDepartmentId()).orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND, "Department doesn't exists"));
        CollegeDepartment collegeDepartment = new CollegeDepartment(college, department);
        if (!collegeDepartmentRepository.existsByCollegeAndDepartment(college, department)) {
            collegeDepartmentRepository.save(collegeDepartment);
        }
        else{
            throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "College already exists with department.");
        }


    }

    @Override
    public CollegeDto getCollegeById(Long collegeId) {

        HelperClass helperClass = new HelperClass();
        College college = collegeRepository.findById(collegeId).orElseThrow(() -> new javax.persistence.EntityNotFoundException("College does not exist."));
        CollegeDto collegeDto = new CollegeDto();
        modelMapper.map(college, collegeDto);
        collegeDto.setCountOfStudent(studentRepository.countByCollegeDepartmentCollegeCollegeId(collegeId));
        collegeDto.setCountOfPrincipal(principalRepository.countByCollegeCollegeId(collegeId));
        collegeDto.setCountOfDepartment(departmentRepository.countByCollegeDepartmentSetCollegeCollegeId(collegeId));
        collegeDto.setCountOfProfessors(collegeDepartmentRepository.countOfProfessorsByDepartmentID(collegeId));
        helperClass.nullUniversityNameAndCollegeCity(collegeDto);
        UniversityDto universityDto = helperClass.nullUniversityCityAndZoneFullName(collegeDto.getUniversity());
        ZoneDto zoneDto = helperClass.nullZoneNameAndCity(collegeDto.getUniversity().getZone());
        universityDto.setZone(zoneDto);
        collegeDto.setUniversity(universityDto);
        return collegeDto;
    }

    @Override
    public Page<CollegeDto> getAllColleges(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<College> colleges = collegeRepository.findAll(pageable);
        List<CollegeDto> collegeDtoList = colleges.stream().map((College college) ->
                new CollegeDto(
                        college.getCollegeId(),
                        college.getCollegeName(),
                        college.getUniversity().getUniversityName(),
                        college.getUniversity().getZone().getZoneFullName())).collect(Collectors.toList());
        return new PageImpl<>(collegeDtoList,  pageable, collegeDtoList.size());

    }

}
