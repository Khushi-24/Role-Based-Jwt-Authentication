package com.example.RoleBasedJwtAuthentication.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDto {

    @NotBlank(message = "Department Id can't be null.")
    private Long departmentId;

    @NotBlank(message = "Department name can't be null.")
    private String departmentName;

    @NotBlank(message = "Duration can't be null.")
    private String duration;

    private Long countOfColleges;

    private Long countOfProfessors;

    private Long countOfStudents;
}
