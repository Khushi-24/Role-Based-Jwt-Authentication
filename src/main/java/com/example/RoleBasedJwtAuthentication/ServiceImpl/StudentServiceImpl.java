package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.RoleDto;
import com.example.RoleBasedJwtAuthentication.Dto.StudentDto;
import com.example.RoleBasedJwtAuthentication.Entity.*;
import com.example.RoleBasedJwtAuthentication.Repository.*;
import com.example.RoleBasedJwtAuthentication.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CollegeDepartmentRepository collegeDepartmentRepository;
    private final CollegeRepository collegeRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        if(!studentRepository.existsById(studentDto.getStudentId())){
            College college = collegeRepository.findById(studentDto.getCollegeDepartmentDto().getCollegeId().longValue()).orElseThrow(()-> new EntityNotFoundException(HttpStatus.NOT_FOUND, "College doesn't exists. "));
            Department department = departmentRepository.findById(studentDto.getCollegeDepartmentDto().getDepartmentId()).orElseThrow(()-> new EntityNotFoundException(HttpStatus.NOT_FOUND, "Department doesn't exists. "));
            if(collegeDepartmentRepository.existsByCollegeAndDepartment(college, department)){
                CollegeDepartment collegeDepartment = collegeDepartmentRepository.findByCollegeIdAndDepartmentId(college.getCollegeId(), department.getDepartmentId());
                Role role = roleRepository.findById("Student").get();
                RoleDto dto = new RoleDto();
                modelMapper.map(role, dto);
                studentDto.setRoleDto(dto);
                Student student = new Student();
//                studentDto.setCollegeDepartment(null);
//                modelMapper.map(studentDto, student);
                student.setStudentId(studentDto.getStudentId());
                student.setStudentName(studentDto.getStudentName());
                student.setSemester(studentDto.getSemester());
                student.setCpi(studentDto.getCpi());
                student.setCollegeDepartment(collegeDepartment);
                student.setRole(role);
                studentRepository.save(student);
                return studentDto;
            }else{
                throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "College and department pair doesn't match.");
            }
        }
        else{
            throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "Student already exists.");
        }

    }

}
