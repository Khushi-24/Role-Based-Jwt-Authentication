package com.example.RoleBasedJwtAuthentication.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class JwtRequest {

    @NotBlank(message = "UserName can,t be null.")
    private String userName;

    @NotBlank(message = "Password can't be null.")
    private String userPassword;
}
