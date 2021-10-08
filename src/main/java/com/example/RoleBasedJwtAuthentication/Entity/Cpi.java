package com.example.RoleBasedJwtAuthentication.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Cpi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Float sem1Cpi;

    @Column
    private Float sem2Cpi;

    @Column
    private Float sem3Cpi;

    @Column
    private Float sem4Cpi;

    @Column
    private Float sem5Cpi;

    @Column
    private Float sem6Cpi;

    @Column
    private Float sem7Cpi;

    @Column
    private Float sem8Cpi;

    @OneToOne
    @JoinColumn(name = "studentId", referencedColumnName = "studentId")
    private Student student;
}
