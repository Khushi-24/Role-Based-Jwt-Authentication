package com.example.RoleBasedJwtAuthentication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "University")
@Getter
@Setter
public class University {

    @Id
    @Column
    private String universityId;

    @Column
    private String universityName;

    @Column
    private String universityCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zoneId", referencedColumnName = "zoneId" )
    private Zone zone;

    @JsonIgnore
    @OneToMany(mappedBy = "collegeId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<College> colleges;
}
