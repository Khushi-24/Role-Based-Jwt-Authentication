package com.example.RoleBasedJwtAuthentication.Service;

import com.example.RoleBasedJwtAuthentication.Dto.DepartmentDto;

public interface DepartmentService {
    DepartmentDto addDepartment(DepartmentDto departmentDto);

    DepartmentDto getDepartmentById(Long departmentId);
}
