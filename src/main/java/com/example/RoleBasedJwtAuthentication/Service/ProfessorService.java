package com.example.RoleBasedJwtAuthentication.Service;

import com.example.RoleBasedJwtAuthentication.Dto.ProfessorDepartmentDto;
import com.example.RoleBasedJwtAuthentication.Dto.ProfessorDto;
import org.springframework.data.domain.Page;

public interface ProfessorService {
    ProfessorDto addProfessor(ProfessorDto professorDto);

    void addProfessorToDepartment(ProfessorDepartmentDto professorDepartmentDto);

    ProfessorDto getProfessorById(String professorId);

    Page<ProfessorDto> getAllProfessor(int pageNo);
}
