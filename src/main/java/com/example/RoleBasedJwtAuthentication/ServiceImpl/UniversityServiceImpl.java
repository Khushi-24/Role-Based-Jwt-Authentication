package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.UniversityDto;
import com.example.RoleBasedJwtAuthentication.Dto.ZoneDto;
import com.example.RoleBasedJwtAuthentication.Entity.University;
import com.example.RoleBasedJwtAuthentication.HelperClass;
import com.example.RoleBasedJwtAuthentication.Repository.CollegeRepository;
import com.example.RoleBasedJwtAuthentication.Repository.UniversityRepository;
import com.example.RoleBasedJwtAuthentication.Repository.ZoneRepository;
import com.example.RoleBasedJwtAuthentication.Service.UniversityService;
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
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

    private final CollegeRepository collegeRepository;

    private final ZoneRepository zoneRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public UniversityDto addUniversity(UniversityDto universityDto) {
        if(!universityRepository.existsById(universityDto.getUniversityId())){
            if(zoneRepository.existsById(universityDto.getZone().getZoneId())){
                University university = new University();
                modelMapper.map(universityDto, university);
                universityRepository.save(university);
                return universityDto;
            }else{
                throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "Please enter a valid zone code, zone code doesn't exists.");
            }
        }else{
            throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "University already exists.");
        }

    }

    @Override
    public UniversityDto getUniversityById(String universityId) {

        if(universityRepository.existsById(universityId)) {
            HelperClass helperClass = new HelperClass();
            University university = universityRepository.findById(universityId).orElseThrow(() -> new javax.persistence.EntityNotFoundException("University does not exist."));
            UniversityDto universityDto = new UniversityDto();
            modelMapper.map(university, universityDto);
            helperClass.nullUniversityCityAndZoneFullName(universityDto);
            ZoneDto zone = helperClass.nullZoneNameAndCity(universityDto.getZone());
            universityDto.setZone(zone);
            universityDto.setCountOfColleges(collegeRepository.countByUniversityUniversityId(universityId));
            universityDto.setCountOfStudents(collegeRepository.countOfStudentsByUniversityId(universityId));
            universityDto.setCountOfDepartments(collegeRepository.countOfDepartmentByUniversityId(universityId));
            universityDto.setCountOfProfessors(collegeRepository.countOfProfessorByUniversityId(universityId));
            return universityDto;

        }else{
            throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "No such university exists");
        }
    }

    @Override
    public Page<UniversityDto> findPaginated(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<University> universities = universityRepository.findAll(pageable);
        List<UniversityDto> universityDtoList = universities.stream().map((University u) ->
                new UniversityDto(
                        u.getUniversityId(),
                        u.getUniversityName(),
                        u.getZone().getZoneFullName())).collect(Collectors.toList());
        return new PageImpl<>(universityDtoList,  pageable, universityDtoList.size());
    }
}
