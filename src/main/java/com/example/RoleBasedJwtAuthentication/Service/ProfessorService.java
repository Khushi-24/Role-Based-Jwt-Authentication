package com.example.RoleBasedJwtAuthentication.Service;

import com.example.RoleBasedJwtAuthentication.Dto.ProfessorDepartmentDto;
import com.example.RoleBasedJwtAuthentication.Dto.ProfessorDto;

public interface ProfessorService {
    ProfessorDto addProfessor(ProfessorDto professorDto);

    void addProfessorToDepartment(ProfessorDepartmentDto professorDepartmentDto);
}
