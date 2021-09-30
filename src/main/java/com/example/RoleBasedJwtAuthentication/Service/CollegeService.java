package com.example.RoleBasedJwtAuthentication.Service;

import com.example.RoleBasedJwtAuthentication.Dto.CollegeDepartmentDto;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDto;

public interface CollegeService {
    CollegeDto addCollege(CollegeDto collegeDto);

    void addDepartmentToCollege(CollegeDepartmentDto collegeDepartmentDto);
}
