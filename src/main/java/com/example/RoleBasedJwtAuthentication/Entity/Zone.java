package com.example.RoleBasedJwtAuthentication.Entity;

import com.example.RoleBasedJwtAuthentication.Entity.University;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "universityId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<University> universityList;

}
