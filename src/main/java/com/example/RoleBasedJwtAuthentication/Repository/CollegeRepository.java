package com.example.RoleBasedJwtAuthentication.Repository;

import com.example.RoleBasedJwtAuthentication.Entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<College ,Long> {

    Long countByUniversityUniversityId(String universityId);

}
