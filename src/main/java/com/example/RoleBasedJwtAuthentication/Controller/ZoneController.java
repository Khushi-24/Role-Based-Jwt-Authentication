package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.ZoneDto;
import com.example.RoleBasedJwtAuthentication.Service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@Validated
public class ZoneController {

    private final ZoneService zoneService;

    @PostMapping("/addZone")
    public ResponseEntity<?> addZone(@Valid @RequestBody ZoneDto zoneDto){
        ZoneDto dto = zoneService.addZone(zoneDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('Principal')")
    @GetMapping("/getZoneById/{zoneId}")
    public ResponseEntity<?> getZoneById(@PathVariable String zoneId){
        ZoneDto dto = zoneService.getZoneById(zoneId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getAllZones/{pageNo}")
    @PreAuthorize("hasRole('student')")
    public ResponseEntity<?> getZoneInPages(@PathVariable(value = "pageNo") int pageNo){
        //getting page of zoneDto
        Page<ZoneDto> page = zoneService.getZoneInPages(pageNo);
        List<ZoneDto> zoneList = page.getContent();
        if(pageNo > page.getTotalPages()){
            throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "No further pages available.");
        }
        return new ResponseEntity<>(zoneList, HttpStatus.OK);
    }
}
