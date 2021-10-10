package com.example.RoleBasedJwtAuthentication.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CpiDto {

    @NotNull(message = "Sem 1 cpi is required.")
    private Float sem1Cpi;

    @NotNull(message = "Sem 2 cpi is required.")
    private Float sem2Cpi;

    @NotNull(message = "Sem 3 cpi is required.")
    private Float sem3Cpi;

    @NotNull(message = "Sem 4 cpi is required.")
    private Float sem4Cpi;

    @NotNull(message = "Sem 5 cpi is required.")
    private Float sem5Cpi;

    @NotNull(message = "Sem 6 cpi is required.")
    private Float sem6Cpi;

    @NotNull(message = "Sem 7 cpi is required.")
    private Float sem7Cpi;

    @NotNull(message = "Sem 8 cpi is required.")
    private Float sem8Cpi;

    public interface AddStudent{}
}
