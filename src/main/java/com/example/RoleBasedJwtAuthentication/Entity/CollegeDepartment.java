package com.example.RoleBasedJwtAuthentication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "CollegeDepartment")
@Getter
@Setter
@NoArgsConstructor
public class CollegeDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long collegeDepartmentId;

    @ManyToOne
    @JoinColumn
    private College college;

    @ManyToOne
    @JoinColumn
    private Department department;

    public CollegeDepartment(College college, Department department) {
        this.college = college;
        this.department = department;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "studentId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Student> studentSet = new HashSet<>();
}
