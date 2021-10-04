package com.example.RoleBasedJwtAuthentication.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "ProfessorDepartment")
@Getter
@Setter
public class ProfessorDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long professorDepartmentId;

    @ManyToOne
    @JoinColumn(name = "professorId", referencedColumnName = "professorId")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "departmentId", referencedColumnName = "departmentId")
    private Department department;

    public ProfessorDepartment(Professor professor, Department department) {
        this.professor =professor;
        this.department =department;
    }
}

