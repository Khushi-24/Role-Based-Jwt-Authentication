package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.Dto.RoleDto;
import com.example.RoleBasedJwtAuthentication.Entity.Role;
import com.example.RoleBasedJwtAuthentication.Repository.RoleRepository;
import com.example.RoleBasedJwtAuthentication.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public RoleDto addNewRole(RoleDto roleDto) {
        if(!roleRepository.existsById(roleDto.getRoleName())){
            Role role = new Role();
            modelMapper.map(roleDto, role);
            roleRepository.save(role);
            return roleDto;
        }
        else{
            throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "Role already exists.");
        }
    }
}
