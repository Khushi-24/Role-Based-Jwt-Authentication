package com.example.RoleBasedJwtAuthentication.Repository;

import com.example.RoleBasedJwtAuthentication.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Long countByCollegeDepartmentSetCollegeCollegeId(Long collegeId);


    @Query(value = "SELECT COUNT(DISTINCT c.university_id) FROM department as d Inner Join college_department as cd On d.department_id= cd.department_department_id INNER JOIN college as c On c.college_id = cd.college_college_id WHERE d.department_id= ?", nativeQuery = true)
    Long countUniversityByDepartmentId(Long departmentId);

    @Query(value = "SELECT COUNT(DISTINCT u.zone_id) FROM department as d Inner Join college_department as cd On d.department_id= cd.department_department_id INNER JOIN college as c On c.college_id = cd.college_college_id Inner Join university as u on c.university_id= u.university_id WHERE d.department_id= ?", nativeQuery = true)
    Long countOfZonesByDepartmentId(Long departmentId);
}
