package com.example.RoleBasedJwtAuthentication.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class User {

    @Id
    private String userName;

    @Column
    private String userPassword;

    @Column
    private String userRole;
}
