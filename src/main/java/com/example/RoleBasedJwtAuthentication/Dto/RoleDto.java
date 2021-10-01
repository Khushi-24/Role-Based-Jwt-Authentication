package com.example.RoleBasedJwtAuthentication.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto {

    @NotNull(message = "Role type is required.")
    private String roleName;

    @NotNull(message = "Role Description is required.")
    private String roleDescription;
}
