package com.example.RoleBasedJwtAuthentication.Dto;

import com.example.RoleBasedJwtAuthentication.Entity.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    private Student student;
    private String jwtToken;

    public JwtResponse(Student student, String jwtToken) {
        this.student = student;
        this.jwtToken = jwtToken;
    }
}
