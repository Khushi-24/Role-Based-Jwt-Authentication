package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.*;
import com.example.RoleBasedJwtAuthentication.Service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @PostMapping("/addProfessor")
    @PreAuthorize("hasAnyRole('Teacher','Principal')")
    public ResponseEntity<?> addProfessor(@RequestBody ProfessorDto professorDto){
        ProfessorDto dto = professorService.addProfessor(professorDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/addProfessorToDepartment")
    @PreAuthorize("hasRole('Principal')")
    public ResponseEntity<?> addProfessorToDepartment(@RequestBody ProfessorDepartmentDto professorDepartmentDto){
        professorService.addProfessorToDepartment(professorDepartmentDto);
        return ResponseEntity.ok("Professor Added to department successfully.");
    }

    @GetMapping("/getProfessorById/{professorId}")
    @PreAuthorize("hasAnyRole('Teacher','Principal')")
    public ResponseEntity<?> getProfessorById(@PathVariable String professorId){
        ProfessorDto dto = professorService.getProfessorById(professorId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getAllProfessor/{pageNo}")
    @PreAuthorize("hasAnyRole('Teacher','Principal','student')")
    public ResponseEntity<?> getAllProfessor(@PathVariable(value = "pageNo") int pageNo){
        Page<ProfessorDto> page = professorService.getAllProfessor(pageNo);
        List<ProfessorDto> professorDto = page.getContent();
        if(pageNo > page.getTotalPages()){
            throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "No further pages available.");
        }
        return new ResponseEntity<>(professorDto, HttpStatus.OK);
    }
}
