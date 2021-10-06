package com.example.RoleBasedJwtAuthentication.Repository;

import com.example.RoleBasedJwtAuthentication.Entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<College ,Long> {

    Long countByUniversityUniversityId(String universityId);

    @Query(value = "SELECT COUNT(DISTINCT s.student_id) FROM college as c Inner JOIN college_department as cd On c.college_id= cd.college_college_id Inner JOIN student as s On s.college_department_id = cd.college_department_id where c.university_id=?", nativeQuery = true)
    Long countOfStudentsByUniversityId(String universityId);

    @Query(value = "SELECT Count(DISTINCT d.department_id) FROM college as c Inner JOIN college_department as cd On c.college_id= cd.college_college_id Inner JOIN department as d On d.department_id = cd.department_department_id where c.university_id=?", nativeQuery = true)
    Long countOfDepartmentByUniversityId(String universityId);

    @Query(value = "SELECT COUNT(DISTINCT p.professor_id) FROM college as c Inner JOIN college_department as cd On c.college_id= cd.college_college_id Inner JOIN professor_department as p On cd.department_department_id= p.department_id where c.university_id=?", nativeQuery = true)
    Long countOfProfessorByUniversityId(String universityId);

}
