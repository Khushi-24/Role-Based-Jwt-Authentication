package com.example.RoleBasedJwtAuthentication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Department")
@Getter
@Setter
public class Department {

    @Id
    @Column
    private Long departmentId;

    @Column
    private String departmentName;

    @Column
    private String duration;

    @JsonIgnore
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CollegeDepartment> collegeDepartmentSet = new HashSet<>();



}
