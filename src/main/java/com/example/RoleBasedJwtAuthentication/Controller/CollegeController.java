package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDepartmentDto;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDto;
import com.example.RoleBasedJwtAuthentication.Dto.UniversityDto;
import com.example.RoleBasedJwtAuthentication.Service.CollegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;

    @PostMapping("/addCollege")
    public ResponseEntity<?> addCollege(@RequestBody CollegeDto collegeDto){
        CollegeDto dto = collegeService.addCollege(collegeDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @PostMapping("/addDepartmentToCollege")
    public ResponseEntity<?> addDepartmentToCollege(@RequestBody CollegeDepartmentDto collegeDepartmentDto){
        collegeService.addDepartmentToCollege(collegeDepartmentDto);
        return ResponseEntity.ok("Department Added to college successfully.");
    }

    @GetMapping("/getCollegeById/{collegeId}")
    public ResponseEntity<?> getCollegeById(@PathVariable Long collegeId){
        CollegeDto dto = collegeService.getCollegeById(collegeId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getAllCourse/{pageNo}")
    public ResponseEntity<?> getAllCourse(@PathVariable(value = "pageNo") int pageNo){
        Page<CollegeDto> page = collegeService.getAllCourse(pageNo);
        List<CollegeDto> collegeDtoList = page.getContent();
        if(pageNo > page.getTotalPages()){
            throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "No further pages available.");
        }
        return new ResponseEntity<>(collegeDtoList, HttpStatus.OK);
    }
}
