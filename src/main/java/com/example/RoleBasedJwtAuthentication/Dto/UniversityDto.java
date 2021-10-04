package com.example.RoleBasedJwtAuthentication.Dto;

import com.example.RoleBasedJwtAuthentication.Dto.ZoneDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UniversityDto {

    @NotBlank(message = "University Id can't be null.")
    private String universityId;

    @NotBlank(message = "University name can't be null.")
    private String universityName;

    @NotBlank(message = "University city can't be null.")
    private String universityCity;

    @NotBlank(message = "Please mention zone code for university.")
    private ZoneDto zone;

    private Long countOfColleges ;
}
