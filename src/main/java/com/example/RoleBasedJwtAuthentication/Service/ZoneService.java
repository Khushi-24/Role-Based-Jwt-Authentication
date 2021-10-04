package com.example.RoleBasedJwtAuthentication.Service;

import com.example.RoleBasedJwtAuthentication.Dto.ZoneDto;
import com.example.RoleBasedJwtAuthentication.Dto.UniversityDto;
import com.example.RoleBasedJwtAuthentication.Entity.Zone;
import org.springframework.data.domain.Page;

public interface ZoneService {

    ZoneDto addZone(ZoneDto zoneDto);

    ZoneDto getZoneById(String zoneId);

    Page<Zone> findPaginated(int pageNo);
}
