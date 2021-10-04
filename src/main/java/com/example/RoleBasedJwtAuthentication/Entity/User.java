package com.example.RoleBasedJwtAuthentication.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    private String userName;

    @Column
    private String userPassword;

    @Column
    private String userRole;
}
