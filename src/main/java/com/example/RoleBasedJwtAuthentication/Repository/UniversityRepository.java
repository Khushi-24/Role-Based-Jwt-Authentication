package com.example.RoleBasedJwtAuthentication.Repository;

import com.example.RoleBasedJwtAuthentication.Entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, String> {

    Long countByZoneZoneId(String zoneId);

    @Query(value = "SELECT Count(*) FROM university as u Inner Join college as c on u.university_id = c.university_id WHERE u.zone_id= ?", nativeQuery = true)
    Long countCollegesByZoneId(String zoneId);

    @Query(value = "SELECT Count(*) FROM university as u Inner Join college as c on u.university_id = c.university_id Inner JOIN college_department as cd On c.college_id= cd.college_college_id Inner JOIN student as s ON cd.college_department_id = s.college_department_id WHERE u.zone_id=?", nativeQuery = true)
    Long countOfStudentsByZoneId(String zoneId);

    @Query(value = "SELECT COUNT(DISTINCT d.department_id) FROM university as u Inner Join college as c on u.university_id = c.university_id Inner JOIN college_department as cd On c.college_id= cd.college_college_id Inner JOIN department as d On d.department_id = cd.department_department_id where u.zone_id =?", nativeQuery = true)
    Long countOfDepartmentByZoneId(String zoneId);

    @Query(value = "SELECT COUNT(DISTINCT pd.professor_id) FROM university as u Inner Join college as c on u.university_id = c.university_id Inner JOIN college_department as cd On c.college_id= cd.college_college_id Inner JOIN professor_department as pd on pd.department_id = cd.department_department_id where u.zone_id =?", nativeQuery = true)
    Long countOfProfessorByZoneId(String zoneId);

}
