package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.Dto.CollegeDepartmentDto;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDto;
import com.example.RoleBasedJwtAuthentication.Service.CollegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/getCollegeById/{collegeId}")
    public ResponseEntity<?> getCollegeById(@PathVariable Long collegeId){
        CollegeDto dto = collegeService.getCollegeById(collegeId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
