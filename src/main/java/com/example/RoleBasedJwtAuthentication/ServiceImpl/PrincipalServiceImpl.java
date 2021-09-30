package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.PrincipalDto;
import com.example.RoleBasedJwtAuthentication.Entity.Principal;
import com.example.RoleBasedJwtAuthentication.Repository.CollegeRepository;
import com.example.RoleBasedJwtAuthentication.Repository.PrincipalRepository;
import com.example.RoleBasedJwtAuthentication.Service.PrincipalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalServiceImpl implements PrincipalService {

    private final PrincipalRepository principalRepository;
    private final CollegeRepository collegeRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public PrincipalDto addPrincipal(PrincipalDto principalDto) {
        if(!principalRepository.existsById(principalDto.getPrincipalId())){
            if(collegeRepository.existsById(principalDto.getCollege().getCollegeId())){
                if(principalRepository.existsByCollegeCollegeId(principalDto.getCollege().getCollegeId())){
                    throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "One college can have only one Principal.");
                }else{
                    Principal principal = new Principal();
                    modelMapper.map(principalDto, principal);
                    principalRepository.save(principal);
                    return principalDto;
                }

            }else{
                throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "Please enter valid college id, college id doesn't exists.");
            }
        }else{
            throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "Principal Already Exists.");
        }

    }
}
