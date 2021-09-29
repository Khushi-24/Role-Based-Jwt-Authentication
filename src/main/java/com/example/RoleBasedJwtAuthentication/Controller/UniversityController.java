package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.Dto.UniversityDto;
import com.example.RoleBasedJwtAuthentication.Service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @PostMapping("/addUniversity")
    public ResponseEntity<?> addUniversity(@RequestBody UniversityDto universityDto){
        UniversityDto dto = universityService.addUniversity(universityDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
