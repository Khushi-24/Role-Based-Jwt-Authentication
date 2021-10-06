package com.example.RoleBasedJwtAuthentication.Dto;

import com.example.RoleBasedJwtAuthentication.Entity.CollegeDepartment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDto {

    @NotBlank(message = "Department Id can't be null.")
    private Long departmentId;

    @NotBlank(message = "Department name can't be null.")
    private String departmentName;

    @NotBlank(message = "Duration can't be null.")
    private String duration;

    private Long countOfColleges;

    private Long countOfUniversities;

    private Long countOfZones;

    private Long countOfProfessors;

    private Long countOfStudents;

    public DepartmentDto(Long departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }
}
