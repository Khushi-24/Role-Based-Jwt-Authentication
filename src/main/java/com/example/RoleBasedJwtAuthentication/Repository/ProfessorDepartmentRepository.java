package com.example.RoleBasedJwtAuthentication.Repository;

import com.example.RoleBasedJwtAuthentication.Entity.Department;
import com.example.RoleBasedJwtAuthentication.Entity.Professor;
import com.example.RoleBasedJwtAuthentication.Entity.ProfessorDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorDepartmentRepository extends JpaRepository<ProfessorDepartment, Long> {
    boolean existsByProfessorAndDepartment(Professor professor, Department department);

    Long countByDepartmentDepartmentId(Long departmentId);
}
