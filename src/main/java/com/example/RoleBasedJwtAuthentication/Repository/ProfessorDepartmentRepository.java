package com.example.RoleBasedJwtAuthentication.Repository;

import com.example.RoleBasedJwtAuthentication.Entity.Department;
import com.example.RoleBasedJwtAuthentication.Entity.Professor;
import com.example.RoleBasedJwtAuthentication.Entity.ProfessorDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorDepartmentRepository extends JpaRepository<ProfessorDepartment, Long> {
    boolean existsByProfessorAndDepartment(Professor professor, Department department);

    Long countByDepartmentDepartmentId(Long departmentId);

    Long countByProfessorProfessorId(String professorId);

    @Query(value = "SELECT Count(DISTINCT cd.college_college_id) from professor_department as pd INNER Join college_department as cd On pd.department_id = cd.department_department_id where professor_id=?", nativeQuery = true)
    Long countOfCollegesByProfessorId(String professorId);

    @Query(value = "SELECT COUNT(DISTINCT c.university_id) from professor_department as pd INNER Join college_department as cd On pd.department_id = cd.department_department_id INNER Join college as c On c.college_id = cd.college_college_id where professor_id=?", nativeQuery = true)
    Long countOfUniversitiesByProfessorId(String professorId);

    @Query(value = "SELECT COUNT(DISTINCT u.zone_id) from professor_department as pd INNER Join college_department as cd On pd.department_id = cd.department_department_id INNER Join college as c On c.college_id = cd.college_college_id INNER JOIN university as u On c.university_id = u.university_id where professor_id= ?",nativeQuery = true)
    Long countOfZonesByProfessorId(String professorId);
}
