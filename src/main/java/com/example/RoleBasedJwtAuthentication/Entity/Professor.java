package com.example.RoleBasedJwtAuthentication.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Professor")
@Getter
@Setter
public class Professor {

    @Id
    private String professorId;

    @Column
    private String professorName;

    @Column
    private String professorPassword;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ProfessorDepartment> professorDepartments = new HashSet<>();
}
