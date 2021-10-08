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

    public PrincipalDto(String principalId, String principalName) {
        this.principalId = principalId;
        this.principalName = principalName;
    }
}

