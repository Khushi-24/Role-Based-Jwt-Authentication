package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.BadRequestException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.UniversityDto;
import com.example.RoleBasedJwtAuthentication.Entity.University;
import com.example.RoleBasedJwtAuthentication.Repository.UniversityRepository;
import com.example.RoleBasedJwtAuthentication.Repository.ZoneRepository;
import com.example.RoleBasedJwtAuthentication.Service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

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
}
