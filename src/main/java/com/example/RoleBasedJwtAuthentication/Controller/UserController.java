package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.Entity.User;
import com.example.RoleBasedJwtAuthentication.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/disableUser/{userId}")
    @PreAuthorize("hasRole('Principal')")
    public ResponseEntity<?> disableUser(@PathVariable String userId){
        User user = userService.disableUser(userId);
        return ResponseEntity.ok("User with userId = " + userId + "is disabled successfully.");
    }
}
