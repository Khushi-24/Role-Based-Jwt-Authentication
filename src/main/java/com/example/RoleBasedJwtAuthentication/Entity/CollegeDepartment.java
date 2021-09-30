package com.example.RoleBasedJwtAuthentication.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "CollegeDepartment")
@Getter
@Setter
public class CollegeDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long collegeDepartmentId;

    @ManyToOne
    @JoinColumn
    private College college;

    @ManyToOne
    @JoinColumn
    private Department department;

    public CollegeDepartment(College college, Department department) {
        this.college = college;
        this.department = department;
    }
}
