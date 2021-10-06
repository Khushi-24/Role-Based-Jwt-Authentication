package com.example.RoleBasedJwtAuthentication.Service;

import com.example.RoleBasedJwtAuthentication.Dto.DepartmentDto;
import org.springframework.data.domain.Page;

public interface DepartmentService {
    DepartmentDto addDepartment(DepartmentDto departmentDto);

    DepartmentDto getDepartmentById(Long departmentId);

    Page<DepartmentDto> getAllDepartment(int pageNo);
}
