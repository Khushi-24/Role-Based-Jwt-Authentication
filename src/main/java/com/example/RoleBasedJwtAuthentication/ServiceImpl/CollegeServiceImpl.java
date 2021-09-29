package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDto;
import com.example.RoleBasedJwtAuthentication.Entity.College;
import com.example.RoleBasedJwtAuthentication.Repository.CollegeRepository;
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
}
