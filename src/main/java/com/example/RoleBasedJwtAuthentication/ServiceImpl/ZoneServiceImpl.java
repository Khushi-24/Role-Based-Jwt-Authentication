package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.BadRequestException;
import com.example.RoleBasedJwtAuthentication.Dto.ZoneDto;
import com.example.RoleBasedJwtAuthentication.Entity.Zone;
import com.example.RoleBasedJwtAuthentication.Repository.ZoneRepository;
import com.example.RoleBasedJwtAuthentication.Service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public ZoneDto addZone(ZoneDto zoneDto) {

        if(!zoneRepository.existsById(zoneDto.getZoneId())){
            if(!zoneRepository.existsByZoneName(zoneDto.getZoneName())){
                if(!zoneRepository.existsByZoneFullName(zoneDto.getZoneFullName())){
                    Zone zone = new Zone();
                    modelMapper.map(zoneDto, zone);
                    zoneRepository.save(zone);
                    return zoneDto;
                }
                else{
                    throw new BadRequestException(HttpStatus.BAD_REQUEST, "Zone name already exists, 2 id's can't have same zone name.");
                }
            }else{
                throw new BadRequestException(HttpStatus.BAD_REQUEST, "2 id's can't have same zone name.");
            }
        }else {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Zone Id already exists.");
        }
    }
}
