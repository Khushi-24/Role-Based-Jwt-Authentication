package com.example.RoleBasedJwtAuthentication.Repository;

import com.example.RoleBasedJwtAuthentication.Entity.College;
import com.example.RoleBasedJwtAuthentication.Entity.CollegeDepartment;
import com.example.RoleBasedJwtAuthentication.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeDepartmentRepository extends JpaRepository<CollegeDepartment, Long> {

    boolean existsByCollegeAndDepartment(College college, Department department);
}
