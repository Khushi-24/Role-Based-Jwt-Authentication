package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.Dto.StudentDto;
import com.example.RoleBasedJwtAuthentication.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/addStudent")
    public ResponseEntity<?> addStudent(@RequestBody StudentDto studentDto){
        StudentDto dto = studentService.addStudent(studentDto);
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
