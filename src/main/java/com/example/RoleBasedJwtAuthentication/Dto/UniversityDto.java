package com.example.RoleBasedJwtAuthentication.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
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

    private String zoneFullName;

    private Long countOfColleges;

    private Long countOfStudents;

    private Long countOfDepartments;

    private Long countOfProfessors;

    public UniversityDto(String universityId, String universityName, String zoneFullName) {
        this.universityId = universityId;
        this.universityName = universityName;
        this.zoneFullName = zoneFullName;
    }


}
