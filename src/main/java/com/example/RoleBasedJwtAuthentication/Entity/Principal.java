package com.example.RoleBasedJwtAuthentication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Principal {

    @Id
    @Column
    private String principalId;

    @Column
    private String principalName;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "collegeId", referencedColumnName = "collegeId")
    private College college;
}
