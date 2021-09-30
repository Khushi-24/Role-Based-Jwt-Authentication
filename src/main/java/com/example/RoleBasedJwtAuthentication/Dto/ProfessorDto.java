package com.example.RoleBasedJwtAuthentication.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProfessorDto {


    private Long professorId;

    @NotNull(message ="Professor name can't be null")
    private String professorName;
}
