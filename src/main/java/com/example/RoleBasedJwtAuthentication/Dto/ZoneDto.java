package com.example.RoleBasedJwtAuthentication.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZoneDto {

    @NotBlank(message = "ZoneId can't be null.")
    private String zoneId;

    @NotBlank(message = "Zone name can't be null.")
    @Size(min = 2, message = "Zone name can't have less than two character.")
    private String zoneName;

    @NotBlank(message = "Zone name can't be null")
    @Size(min = 10 , message = "Zone FullName can't have less than ten character.")
    private String zoneFullName;

    @NotBlank(message = "Zone state can't be null.")
    private String state;

    private Long countOfUniversities;

    private Long countOfColleges;

    private Long countOfStudents;

    private Long countOfDepartments;

    private Long countOfProfessors;

    public ZoneDto(String zoneId, String zoneFullName) {
        this.zoneId = zoneId;
        this.zoneFullName = zoneFullName;
    }
}
