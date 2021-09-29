package com.example.RoleBasedJwtAuthentication.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class College {

    @Id
    @Column
    private Long collegeId;

    @Column
    private String collegeName;

    @Column
    private String collegeCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "universityId", referencedColumnName = "universityId")
    private University university;


}
