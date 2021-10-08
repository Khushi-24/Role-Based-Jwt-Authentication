package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.BadRequestException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.ZoneDto;
import com.example.RoleBasedJwtAuthentication.Entity.Zone;
import com.example.RoleBasedJwtAuthentication.Repository.UniversityRepository;
import com.example.RoleBasedJwtAuthentication.Repository.ZoneRepository;
import com.example.RoleBasedJwtAuthentication.Service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final UniversityRepository universityRepository;

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

    @Override
    public ZoneDto getZoneById(String zoneId) {
        Zone zone = zoneRepository.findById(zoneId).orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND,"Zone does not exist."));
        ZoneDto zoneDto = new ZoneDto();
        modelMapper.map(zone, zoneDto);
        zoneDto.setZoneName(null);
        zoneDto.setState(null);
        zoneDto.setCountOfUniversities(universityRepository.countByZoneZoneId(zoneId));
        zoneDto.setCountOfColleges(universityRepository.countCollegesByZoneId(zoneId));
        zoneDto.setCountOfStudents(universityRepository.countOfStudentsByZoneId(zoneId));
        zoneDto.setCountOfDepartments(universityRepository.countOfDepartmentByZoneId(zoneId));
        zoneDto.setCountOfProfessors(universityRepository.countOfProfessorByZoneId(zoneId));
        return zoneDto;
    }

    @Override
    public Page<ZoneDto> getZoneInPages(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<Zone> zoneList = zoneRepository.findAll(pageable);
        List<ZoneDto> zoneDtoPage = zoneList.stream().map((Zone zone) -> new ZoneDto(zone.getZoneId(), zone.getZoneFullName())).collect(Collectors.toList());
        return new PageImpl<>(zoneDtoPage, pageable, zoneDtoPage.size());
    }
}


//without using lambda
//        zoneList.stream().map(new Function<Zone, ZoneDto>() {
//            @Override
//            public ZoneDto apply(Zone zone) {
//                return new ZoneDto(zone.getZoneId(), zone.getZoneFullName());
//        });
