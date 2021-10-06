package com.example.RoleBasedJwtAuthentication;

import com.example.RoleBasedJwtAuthentication.Dto.*;

public class HelperClass {

    public ZoneDto nullZoneNameAndCity(ZoneDto zoneDto){
        zoneDto.setZoneName(null);
        zoneDto.setState(null);
        return zoneDto;
    }

    public UniversityDto nullUniversityCityAndZoneFullName(UniversityDto universityDto){
        universityDto.setUniversityCity(null);
        universityDto.setZoneFullName(null);
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

    public CollegeDto nullUniversityNameAndCollegeCity(CollegeDto collegeDto){
        collegeDto.setCollegeCity(null);
        collegeDto.setUniversityName(null);
        return collegeDto;
    }
}
