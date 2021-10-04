package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.BadRequestException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.UniversityDto;
import com.example.RoleBasedJwtAuthentication.Entity.Zone;
import com.example.RoleBasedJwtAuthentication.Repository.UniversityRepository;
import com.example.RoleBasedJwtAuthentication.Repository.ZoneRepository;
import com.example.RoleBasedJwtAuthentication.Service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final UniversityRepository universityRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public com.example.RoleBasedJwtAuthentication.Dto.ZoneDto addZone(com.example.RoleBasedJwtAuthentication.Dto.ZoneDto zoneDto) {

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

    @Override
    public com.example.RoleBasedJwtAuthentication.Dto.ZoneDto getZoneById(String zoneId) {
        if(zoneRepository.existsById(zoneId)) {
            Zone zone = zoneRepository.findById(zoneId).orElseThrow(() -> new javax.persistence.EntityNotFoundException("Zone does not exist."));
            com.example.RoleBasedJwtAuthentication.Dto.ZoneDto zoneDto = new com.example.RoleBasedJwtAuthentication.Dto.ZoneDto();
            modelMapper.map(zone, zoneDto);
            zoneDto.setCountOfUniversities(universityRepository.countByZoneZoneId(zoneId));
            return zoneDto;
        }else{
            throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "No such zone exists.");
        }
    }

    @Override
    public Page<Zone> findPaginated(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        return this.zoneRepository.findAll(pageable);
    }
}
