package com.example.RoleBasedJwtAuthentication.Service;

import com.example.RoleBasedJwtAuthentication.Dto.RoleDto;

public interface RoleService {
    RoleDto addNewRole(RoleDto roleDto);
}
