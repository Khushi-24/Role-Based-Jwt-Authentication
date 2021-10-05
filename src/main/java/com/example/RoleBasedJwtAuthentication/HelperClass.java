package com.example.RoleBasedJwtAuthentication;

import com.example.RoleBasedJwtAuthentication.Dto.*;

public class HelperClass {

    public ZoneDto nullZoneNameAndCity(ZoneDto zoneDto){
        zoneDto.setZoneName(null);
        zoneDto.setState(null);
        return zoneDto;
    }

    public UniversityDto nullUniversityCity(UniversityDto universityDto){
        universityDto.setUniversityCity(null);
        return universityDto;
    }

    public DepartmentDto nullDepartmentDuration(DepartmentDto departmentDto){
        departmentDto.setDuration(null);
        return departmentDto;
    }

    public StudentDto nullStudentSemesterAndCpiAndPassword(StudentDto studentDto){
        studentDto.setSemester(null);
        studentDto.setCpi(null);
        studentDto.setStudentPassword(null);
        return studentDto;
    }

    public CollegeDto nullCollegeCity(CollegeDto collegeDto){
        collegeDto.setCollegeCity(null);
        return collegeDto;
    }
}
