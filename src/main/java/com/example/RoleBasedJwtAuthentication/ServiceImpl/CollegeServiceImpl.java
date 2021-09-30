package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.BadRequestException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDepartmentDto;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDto;
import com.example.RoleBasedJwtAuthentication.Entity.College;
import com.example.RoleBasedJwtAuthentication.Entity.CollegeDepartment;
import com.example.RoleBasedJwtAuthentication.Entity.Department;
import com.example.RoleBasedJwtAuthentication.Repository.CollegeDepartmentRepository;
import com.example.RoleBasedJwtAuthentication.Repository.CollegeRepository;
import com.example.RoleBasedJwtAuthentication.Repository.DepartmentRepository;
import com.example.RoleBasedJwtAuthentication.Repository.UniversityRepository;
import com.example.RoleBasedJwtAuthentication.Service.CollegeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollegeServiceImpl implements CollegeService {

    private final CollegeRepository collegeRepository;

    private final DepartmentRepository departmentRepository;

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

}
