package com.example.RoleBasedJwtAuthentication.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto {

    @NotNull(message = "Student Id can't be null.")
    private String studentId;

    @NotNull(message = "Student name can't be null.")
    private String studentName;

    @NotNull(message = "Student semester can't be null.")
    private Long semester;

    @NotNull(message = "Student cpi can't be null.")
    private Float cpi;

    @NotNull(message = "Student password can't be null.")
    private String studentPassword;

    @NotNull(message = "Department Id can't be null.")
    private CollegeDepartmentDto collegeDepartment;

    public StudentDto(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }
}
