package com.example.RoleBasedJwtAuthentication.Service;

import com.example.RoleBasedJwtAuthentication.Dto.StudentDto;
import org.springframework.data.domain.Page;

public interface StudentService {
    StudentDto addStudent(StudentDto studentDto);

    StudentDto getStudentByStudentId(String studentId);

    Page<StudentDto> getAllStudents(int pageNo);
}
