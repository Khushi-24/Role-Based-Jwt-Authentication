package com.example.RoleBasedJwtAuthentication.Repository;

import com.example.RoleBasedJwtAuthentication.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    Long countByCollegeDepartmentCollegeCollegeId(Long collegeId);

    @Query(value = "SELECT Count(*) FROM `student` WHERE cpi> ?", nativeQuery = true)
    int countByCpiGraterThan(float cpi);

    @Query(value = "SELECT Count(*) FROM `student` WHERE cpi< ?", nativeQuery = true)
    int countByCpiLessThan(float cpi);

}
