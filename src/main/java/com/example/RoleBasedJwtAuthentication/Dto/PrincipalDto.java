package com.example.RoleBasedJwtAuthentication.Dto;

import com.example.RoleBasedJwtAuthentication.Entity.College;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PrincipalDto {

    @NotBlank(message = "Principal Id can't be null.")
    private String principalId;

    @NotBlank(message = "Principal name can't be null.")
    private String principalName;

    @NotBlank(message = "College Id can't be null.")
    private College college;

}

