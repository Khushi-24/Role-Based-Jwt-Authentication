package com.example.RoleBasedJwtAuthentication.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollegeDto {

    @NotBlank(message = "College Id can't be null.")
    private Long collegeId;

    @NotBlank(message = "College name can't be null.")
    private String collegeName;

    @NotBlank(message = "College city can't be null.")
    private String collegeCity;

    @NotBlank(message = "Please Mention university code.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "universityID", referencedColumnName = "universityId")
    private UniversityDto university;

    private Long countOfStudent;

    private Long countOfPrincipal;

    private Long countOfDepartment;

    private Long countOfProfessors;
}
