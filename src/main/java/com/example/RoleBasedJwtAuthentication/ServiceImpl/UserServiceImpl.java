package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.BadRequestException;
import com.example.RoleBasedJwtAuthentication.Entity.Student;
import com.example.RoleBasedJwtAuthentication.Entity.User;
import com.example.RoleBasedJwtAuthentication.Repository.UserRepository;
import com.example.RoleBasedJwtAuthentication.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User disableUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new javax.persistence.EntityNotFoundException("User does not exist."));
        String role = user.getUserRole();
        if(role.equals("Principal")){
            throw new BadRequestException(HttpStatus.BAD_REQUEST,"Can't disable Principal.");
        }else{
            user.setEnabled(false);
            userRepository.save(user);
        }
        return null;
    }
}
