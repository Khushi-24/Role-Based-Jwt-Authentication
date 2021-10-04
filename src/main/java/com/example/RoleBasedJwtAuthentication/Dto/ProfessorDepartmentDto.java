package com.example.RoleBasedJwtAuthentication.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProfessorDepartmentDto {

    @NotNull(message = "Professor Id can't be null")
    private String professorId;

    @NotNull(message = "Department Id can't be null")
    private Long departmentId;
}
