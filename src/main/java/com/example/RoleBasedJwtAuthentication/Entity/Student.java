package com.example.RoleBasedJwtAuthentication.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Student")
@Getter
@Setter
public class Student {

    @Id
    private String studentId;

    @Column
    private String studentName;

    @Column
    private Long semester;

    @Column
    private Float cpi;

    @Column
    private String studentPassword;

    @ManyToOne
    @JoinColumn(name = "collegeDepartmentId", referencedColumnName = "collegeDepartmentId")
    private CollegeDepartment collegeDepartment;

}
