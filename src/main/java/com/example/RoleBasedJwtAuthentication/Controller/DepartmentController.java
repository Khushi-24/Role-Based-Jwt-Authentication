package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.Dto.DepartmentDto;
import com.example.RoleBasedJwtAuthentication.Service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/addDepartment")
    public ResponseEntity<?> addDepartment(@RequestBody DepartmentDto departmentDto){
        DepartmentDto dto = departmentService.addDepartment(departmentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
