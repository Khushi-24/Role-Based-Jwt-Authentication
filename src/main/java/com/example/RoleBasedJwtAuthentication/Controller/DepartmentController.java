package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.DepartmentDto;
import com.example.RoleBasedJwtAuthentication.Service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/addDepartment")
    @PreAuthorize("hasRole('Principal')")
    public ResponseEntity<?> addDepartment(@RequestBody DepartmentDto departmentDto){
        DepartmentDto dto = departmentService.addDepartment(departmentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getDepartmentById/{departmentId}")
    @PreAuthorize("hasAnyRole('Teacher','Principal','student')")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long departmentId){
        DepartmentDto dto = departmentService.getDepartmentById(departmentId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getAllDepartment/{pageNo}")
    @PreAuthorize("hasAnyRole('Teacher','Principal','student')")
    public ResponseEntity<?> getAllDepartment(@PathVariable(value = "pageNo") int pageNo){
        Page<DepartmentDto> page = departmentService.getAllDepartment(pageNo);
        List<DepartmentDto> departmentDtoList = page.getContent();
        if(pageNo > page.getTotalPages()){
            throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "No further pages available.");
        }
        return new ResponseEntity<>(departmentDtoList, HttpStatus.OK);
    }
}
