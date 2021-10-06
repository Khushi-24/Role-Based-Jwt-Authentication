package com.example.RoleBasedJwtAuthentication.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfessorDto {

    @NotNull(message ="Professor Id can't be null")
    private String professorId;

    @NotNull(message ="Professor name can't be null")
    private String professorName;

    @NotNull(message ="Professor password can't be null")
    private String professorPassword;

    private Long countOfDepartment;

    private Long countOfColleges;

    private Long countOfUniversity;

    private Long countOfZones;


    public ProfessorDto(String professorId, String professorName) {
        this.professorId = professorId;
        this.professorName = professorName;
    }
}
