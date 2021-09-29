package com.example.RoleBasedJwtAuthentication.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

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
}
