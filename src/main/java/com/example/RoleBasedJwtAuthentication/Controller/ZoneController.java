package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.Dto.ZoneDto;
import com.example.RoleBasedJwtAuthentication.Service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

}
