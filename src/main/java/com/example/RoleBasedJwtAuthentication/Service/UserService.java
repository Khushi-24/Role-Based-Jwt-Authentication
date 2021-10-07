package com.example.RoleBasedJwtAuthentication.Service;

import com.example.RoleBasedJwtAuthentication.Entity.User;

public interface UserService {
    User disableUser(String userId);
}
