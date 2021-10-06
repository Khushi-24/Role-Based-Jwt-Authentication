package com.example.RoleBasedJwtAuthentication.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfessorDepartmentDto {

    @NotNull(message = "Professor Id can't be null")
    private String professorId;

    @NotNull(message = "Department Id can't be null")
    private Long departmentId;
}
