package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.BadRequestException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.*;
import com.example.RoleBasedJwtAuthentication.Entity.*;
import com.example.RoleBasedJwtAuthentication.HelperClass;
import com.example.RoleBasedJwtAuthentication.Repository.*;
import com.example.RoleBasedJwtAuthentication.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder;


    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        if(!studentRepository.existsById(studentDto.getStudentId())){
            College college = collegeRepository.findById(studentDto.getCollegeDepartment().getCollegeId().longValue()).orElseThrow(()-> new EntityNotFoundException(HttpStatus.NOT_FOUND, "College doesn't exists. "));
            Department department = departmentRepository.findById(studentDto.getCollegeDepartment().getDepartmentId()).orElseThrow(()-> new EntityNotFoundException(HttpStatus.NOT_FOUND, "Department doesn't exists. "));
            if(collegeDepartmentRepository.existsByCollegeAndDepartment(college, department)){
                CollegeDepartment collegeDepartment = collegeDepartmentRepository.findByCollegeIdAndDepartmentId(college.getCollegeId(), department.getDepartmentId());
                Student student = new Student();
                student.setStudentId(studentDto.getStudentId());
                student.setStudentName(studentDto.getStudentName());
                student.setSemester(studentDto.getSemester());
                student.setCpi(studentDto.getCpi());
                student.setStudentPassword(getEncodedPassword(studentDto.getStudentPassword()));
                student.setCollegeDepartment(collegeDepartment);
                if(!userRepository.existsById(studentDto.getStudentId())){
                    User user = new User();
                    user.setUserName(student.getStudentId());
                    user.setUserPassword(student.getStudentPassword());
                    user.setUserRole("student");
                    userRepository.save(user);
                    studentRepository.save(student);
                    studentDto.setStudentPassword("");
                    return studentDto;
                }else{
                    throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "UserId is already occupied.");
                }

            }else{
                throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "College and department pair doesn't match.");
            }
        }
        else{
            throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "Student already exists.");
        }

    }

    @Override
    public StudentDto getStudentByStudentId(String studentId) {

        HelperClass helperClass = new HelperClass();
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new javax.persistence.EntityNotFoundException("Student does not exist."));
        System.out.println(student.getCollegeDepartment());
        StudentDto studentDto = new StudentDto();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.map(student, studentDto);
        studentDto = helperClass.nullStudentSemesterAndCpiAndPassword(studentDto);
        CollegeDepartmentDto dto = studentDto.getCollegeDepartment();
        dto.setDepartmentId(null);
        DepartmentDto departmentDto = helperClass.nullDepartmentDuration(dto.getDepartment());
        dto.setDepartment(departmentDto);
        CollegeDto collegeDto = helperClass.nullUniversityNameAndCollegeCity(dto.getCollege());
        UniversityDto universityDto = helperClass.nullUniversityCityAndZoneFullName(collegeDto.getUniversity());
        universityDto.setZone(null);
        collegeDto.setUniversity(universityDto);
        collegeDto.setUniversityName(null);
        dto.setCollege(collegeDto);
        studentDto.setCollegeName(null);
        studentDto.setDepartmentName(null);
        return studentDto;
    }

    @Override
    public Page<StudentDto> getAllStudents(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<Student> students = studentRepository.findAll(pageable);
        List<StudentDto> studentDtoList = students.stream().map((Student student) ->
                new StudentDto(
                        student.getStudentId(),
                        student.getStudentName(),
                        student.getCollegeDepartment().getCollege().getCollegeName(),
                        student.getCollegeDepartment().getDepartment().getDepartmentName(),
                        student.getCollegeDepartment().getCollege().getUniversity().getUniversityName(),
                        student.getCollegeDepartment().getCollege().getUniversity().getZone().getZoneFullName()
                        )).collect(Collectors.toList());
        return new PageImpl<>(studentDtoList,  pageable, studentDtoList.size());
    }

    @Override
    public int getStudentHavingCpiGreaterThan(float cpi) {
        if(cpi >=10){
            throw new BadRequestException(HttpStatus.BAD_REQUEST,"Cpi can't be greater than 10.");
        }else {
            int count = studentRepository.countByCpiGraterThan(cpi);
            return count;
        }
    }

    @Override
    public int getStudentHavingCpiLessThan(float cpi) {
        if(cpi >=10){
            throw new BadRequestException(HttpStatus.BAD_REQUEST,"Cpi can't be greater than 10.");
        }else {
            int count = studentRepository.countByCpiLessThan(cpi);
            return count;
        }
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
