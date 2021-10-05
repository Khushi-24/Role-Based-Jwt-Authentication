package com.example.RoleBasedJwtAuthentication.Service;

import com.example.RoleBasedJwtAuthentication.Entity.University;
import com.example.RoleBasedJwtAuthentication.Dto.UniversityDto;
import org.springframework.data.domain.Page;

public interface UniversityService {
    UniversityDto addUniversity(UniversityDto universityDto);

    UniversityDto getUniversityById(String universityId);

    Page<UniversityDto> findPaginated(int pageNo);
}
