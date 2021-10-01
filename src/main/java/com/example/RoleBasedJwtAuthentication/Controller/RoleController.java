package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.Dto.RoleDto;
import com.example.RoleBasedJwtAuthentication.Entity.Role;
import com.example.RoleBasedJwtAuthentication.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/addNewRole")
    public ResponseEntity<?> addNewRole(@RequestBody RoleDto roleDto){
        RoleDto dto = roleService.addNewRole(roleDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
