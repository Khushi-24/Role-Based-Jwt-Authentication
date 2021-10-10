package com.example.RoleBasedJwtAuthentication.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollegeDepartmentDto {

    @NotBlank(message = "College Id can't be null.")
    private Long clgId;

    @NotBlank(message = "Department Id can't be null.")
    private Long depId;

    private DepartmentDto department;
    private CollegeDto college;
}
