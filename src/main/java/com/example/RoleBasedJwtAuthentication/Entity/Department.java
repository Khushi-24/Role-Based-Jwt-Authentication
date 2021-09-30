package com.example.RoleBasedJwtAuthentication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    @OneToMany(mappedBy = "department")
    private Set<CollegeDepartment> collegeDepartmentSet = new HashSet<>();

}
