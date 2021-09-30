package com.example.RoleBasedJwtAuthentication.Repository;

import com.example.RoleBasedJwtAuthentication.Entity.College;
import com.example.RoleBasedJwtAuthentication.Entity.CollegeDepartment;
import com.example.RoleBasedJwtAuthentication.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeDepartmentRepository extends JpaRepository<CollegeDepartment, Long> {

    boolean existsByCollegeAndDepartment(College college, Department department);

    boolean existsByCollegeCollegeIdAndDepartmentDepartmentId(Long collegeId, Long departmentId);

    @Query(value = "Select * from college_department where college_college_id= ?1 And department_department_id=?2 ", nativeQuery = true)
    CollegeDepartment findByCollegeIdAndDepartmentId(Long collegeId, Long departmentId);
}
