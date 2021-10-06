package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDepartmentDto;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDto;
import com.example.RoleBasedJwtAuthentication.Service.CollegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;

    @PostMapping("/addCollege")
    @PreAuthorize("hasRole('Principal')")
    public ResponseEntity<?> addCollege(@RequestBody CollegeDto collegeDto){
        CollegeDto dto = collegeService.addCollege(collegeDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @PostMapping("/addDepartmentToCollege")
    @PreAuthorize("hasRole('Principal')")
    public ResponseEntity<?> addDepartmentToCollege(@RequestBody CollegeDepartmentDto collegeDepartmentDto){
        collegeService.addDepartmentToCollege(collegeDepartmentDto);
        return ResponseEntity.ok("Department Added to college successfully.");
    }

    @GetMapping("/getCollegeById/{collegeId}")
    @PreAuthorize("hasAnyRole('Teacher','Principal','student')")
    public ResponseEntity<?> getCollegeById(@PathVariable Long collegeId){
        CollegeDto dto = collegeService.getCollegeById(collegeId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("/getAllColleges/{pageNo}")
    @PreAuthorize("hasAnyRole('Teacher','Principal','student')")
    public ResponseEntity<?> getAllColleges(@PathVariable(value = "pageNo") int pageNo){
        Page<CollegeDto> page = collegeService.getAllColleges(pageNo);
        List<CollegeDto> collegeDtoList = page.getContent();
        if(pageNo > page.getTotalPages()){
            throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "No further pages available.");
        }
        return new ResponseEntity<>(collegeDtoList, HttpStatus.OK);
    }
}
