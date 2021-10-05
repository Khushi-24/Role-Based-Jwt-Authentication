package com.example.RoleBasedJwtAuthentication.Service;

import com.example.RoleBasedJwtAuthentication.Dto.CollegeDepartmentDto;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDto;
import org.springframework.data.domain.Page;

public interface CollegeService {
    CollegeDto addCollege(CollegeDto collegeDto);

    void addDepartmentToCollege(CollegeDepartmentDto collegeDepartmentDto);

    CollegeDto getCollegeById(Long collegeId);

    Page<CollegeDto> getAllCourse(int pageNo);
}
