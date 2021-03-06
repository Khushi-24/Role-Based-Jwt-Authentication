package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.UniversityDto;
import com.example.RoleBasedJwtAuthentication.Service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @PostMapping("/addUniversity")
    @PreAuthorize("hasRole('Principal')")
    public ResponseEntity<?> addUniversity(@RequestBody UniversityDto universityDto){
        UniversityDto dto = universityService.addUniversity(universityDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getUniversityById/{universityId}")
    @PreAuthorize("hasAnyRole('Teacher','Principal','student')")
    public ResponseEntity<?> getUniversityById(@PathVariable String universityId){
       UniversityDto dto = universityService.getUniversityById(universityId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getAllUniversities/{pageNo}")
    @PreAuthorize("hasAnyRole('Teacher','Principal','student')")
    public ResponseEntity<?> findPaginated(@PathVariable(value = "pageNo") int pageNo){
        Page<UniversityDto> page = universityService.findPaginated(pageNo);
        List<UniversityDto> universityList = page.getContent();
        if(pageNo > page.getTotalPages()){
            throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "No further pages available.");
        }
        return new ResponseEntity<>(universityList, HttpStatus.OK);
    }
}
