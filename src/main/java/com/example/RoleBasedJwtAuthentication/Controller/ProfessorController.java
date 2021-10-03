package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.Dto.ProfessorDto;
import com.example.RoleBasedJwtAuthentication.Service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @PostMapping("/addProfessor")
    public ResponseEntity<?> addProfessor(@RequestBody ProfessorDto professorDto){
        ProfessorDto dto = professorService.addProfessor(professorDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
