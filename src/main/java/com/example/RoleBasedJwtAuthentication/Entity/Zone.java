package com.example.RoleBasedJwtAuthentication.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name ="zone")
@Getter
@Setter
public class Zone {

    @Id
    @Column
    private String zoneId;

    @Column(unique = true)
    private String zoneName;

    @Column(unique = true)
    private String zoneFullName;

    @Column
    private String state;

}
