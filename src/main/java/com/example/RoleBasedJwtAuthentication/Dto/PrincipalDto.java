package com.example.RoleBasedJwtAuthentication.Dto;

import com.example.RoleBasedJwtAuthentication.Entity.College;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrincipalDto {

    @NotBlank(message = "Principal Id can't be null.")
    private String principalId;

    @NotBlank(message = "Principal name can't be null.")
    private String principalName;

    @NotBlank(message = "Principal password can't be null.")
    private String principalPassword;

    @NotBlank(message = "College Id can't be null.")
    private CollegeDto college;

    private Long countOfStudents;

    private String collegeName;
    private String universityName;
    private String zoneFullName;

    public PrincipalDto(String principalId, String principalName, String collegeName, String universityName, String zoneFullName) {
        this.principalId = principalId;
        this.principalName = principalName;
        this.collegeName = collegeName;
        this.universityName = universityName;
        this.zoneFullName = zoneFullName;
    }
}

