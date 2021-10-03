package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.PrincipalDto;
import com.example.RoleBasedJwtAuthentication.Entity.Principal;
import com.example.RoleBasedJwtAuthentication.Entity.User;
import com.example.RoleBasedJwtAuthentication.Repository.CollegeRepository;
import com.example.RoleBasedJwtAuthentication.Repository.PrincipalRepository;
import com.example.RoleBasedJwtAuthentication.Repository.UserRepository;
import com.example.RoleBasedJwtAuthentication.Service.PrincipalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalServiceImpl implements PrincipalService {

    private final PrincipalRepository principalRepository;
    private final UserRepository userRepository;
    private final CollegeRepository collegeRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder;

    @Override
    public PrincipalDto addPrincipal(PrincipalDto principalDto) {
        if(!principalRepository.existsById(principalDto.getPrincipalId())){
            if(collegeRepository.existsById(principalDto.getCollege().getCollegeId())){
                if(principalRepository.existsByCollegeCollegeId(principalDto.getCollege().getCollegeId())){
                    throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "One college can have only one Principal.");
                }else{
                    Principal principal = new Principal();
                    User user = new User();
                    modelMapper.map(principalDto, principal);
                    principal.setPrincipalPassword(getEncodedPassword(principalDto.getPrincipalPassword()));
                    principalRepository.save(principal);
                    user.setUserName(principal.getPrincipalName());
                    user.setUserPassword(principal.getPrincipalPassword());
                    user.setUserRole("Principal");
                    userRepository.save(user);
                    return principalDto;
                }

            }else{
                throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "Please enter valid college id, college id doesn't exists.");
            }
        }else{
            throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "Principal Already Exists.");
        }

    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
