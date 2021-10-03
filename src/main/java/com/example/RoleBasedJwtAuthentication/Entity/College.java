package com.example.RoleBasedJwtAuthentication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class College {

    @Id
    private Long collegeId;

    @Column
    private String collegeName;

    @Column
    private String collegeCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "universityId", referencedColumnName = "universityId")
    private University university;

    @JsonIgnore
    @OneToOne(mappedBy = "college")
    private Principal principal;

    @JsonIgnore
    @OneToMany(mappedBy = "college",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CollegeDepartment> collegeDepartmentSet = new HashSet<>();

}
