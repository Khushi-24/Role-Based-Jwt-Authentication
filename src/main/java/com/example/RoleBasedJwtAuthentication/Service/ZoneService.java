package com.example.RoleBasedJwtAuthentication.Service;

import com.example.RoleBasedJwtAuthentication.Dto.ZoneDto;
import org.springframework.data.domain.Page;

public interface ZoneService {

    ZoneDto addZone(ZoneDto zoneDto);

    ZoneDto getZoneById(String zoneId);

    Page<ZoneDto> getZoneInPages(int pageNo);
}
