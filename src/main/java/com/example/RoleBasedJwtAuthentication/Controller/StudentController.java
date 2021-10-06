package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDto;
import com.example.RoleBasedJwtAuthentication.Dto.StudentDto;
import com.example.RoleBasedJwtAuthentication.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/addStudent")
    public ResponseEntity<?> addStudent(@RequestBody StudentDto studentDto){
        StudentDto dto = studentService.addStudent(studentDto);
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getStudentByStudentId/{studentId}")
    public ResponseEntity<?> getStudentByStudentId(@PathVariable String studentId){
        StudentDto dto = studentService.getStudentByStudentId(studentId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getAllStudents/{pageNo}")
    public ResponseEntity<?> getAllStudents(@PathVariable(value = "pageNo") int pageNo){
        Page<StudentDto> page = studentService.getAllStudents(pageNo);
        List<StudentDto> dto = page.getContent();
        if(pageNo > page.getTotalPages()){
            throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "No further pages available.");
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getStudentHavingCpiGreaterThan/{cpi}")
    public ResponseEntity<?> getStudentHavingCpiGreaterThan(@PathVariable float cpi){
        int count= studentService.getStudentHavingCpiGreaterThan(cpi);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/getStudentHavingCpiLessThan/{cpi}")
    public ResponseEntity<?> getStudentHavingCpiLessThan(@PathVariable float cpi){
        int count= studentService.getStudentHavingCpiLessThan(cpi);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
