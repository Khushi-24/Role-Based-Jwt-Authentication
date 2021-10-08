package com.example.RoleBasedJwtAuthentication.Repository;

import com.example.RoleBasedJwtAuthentication.Entity.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PrincipalRepository extends JpaRepository<Principal, String> {
    boolean existsByCollegeCollegeId(Long collegeId);

    @Query(value = "SELECT COUNT(DISTINCT s.student_id) from principal as p INNER JOIN college as c On p.college_id = c.college_id INNER JOIN college_department as cd on c.college_id = cd.college_college_id Inner Join student as s On s.college_department_id= cd.college_department_id where principal_id=?", nativeQuery = true)
    Long countStudentByPrincipalId(String principalId);
}
