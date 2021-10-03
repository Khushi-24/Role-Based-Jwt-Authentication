package com.example.RoleBasedJwtAuthentication.Repository;

import com.example.RoleBasedJwtAuthentication.Entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, String> {
}
