package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.BadRequestException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.*;
import com.example.RoleBasedJwtAuthentication.Entity.*;
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
    private final ModelMapper modelMapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder;
    private final CpiRepository cpiRepository;


    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        if(!studentRepository.existsById(studentDto.getStudentId())){
            College college = collegeRepository.findById(studentDto.getCollegeDepartment().getClgId()).orElseThrow(()-> new EntityNotFoundException(HttpStatus.NOT_FOUND, "College doesn't exists. "));
            Department department = departmentRepository.findById(studentDto.getCollegeDepartment().getDepId()).orElseThrow(()-> new EntityNotFoundException(HttpStatus.NOT_FOUND, "Department doesn't exists. "));
            if(collegeDepartmentRepository.existsByCollegeAndDepartment(college, department)){
                CollegeDepartment collegeDepartment = collegeDepartmentRepository.findByCollegeIdAndDepartmentId(college.getCollegeId(), department.getDepartmentId());
                    Student student = new Student();
                    student.setStudentId(studentDto.getStudentId());
                    student.setStudentName(studentDto.getStudentName());
                    student.setSemester(studentDto.getSemester());
                    student.setStudentPassword(getEncodedPassword(studentDto.getStudentPassword()));
                    student.setCollegeDepartment(collegeDepartment);
                    getSem(studentDto.getSemester(), studentDto.getCpiDto());
                    Cpi cpi = new Cpi();
                    CpiDto cpiDto = studentDto.getCpiDto();
                    modelMapper.map(cpiDto, cpi);
                    cpi.setStudent(student);
                    cpiRepository.save(cpi);
                    if (!userRepository.existsById(studentDto.getStudentId())) {
                        User user = new User();
                        user.setUserName(student.getStudentId());
                        user.setUserPassword(student.getStudentPassword());
                        user.setUserRole("student");
                        userRepository.save(user);
                        studentRepository.save(student);
                        studentDto.setStudentPassword("");
                        return studentDto;
                    } else {
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

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND,"Student does not exist."));
        System.out.println(student.getCollegeDepartment());
        StudentDto studentDto = new StudentDto();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.map(student, studentDto);
        studentDto.setSemester(null);
        studentDto.setStudentPassword(null);
        CollegeDepartmentDto dto = studentDto.getCollegeDepartment();
        dto.setDepId(null);
        DepartmentDto departmentDto =dto.getDepartment();
        departmentDto.setDuration(null);
        dto.setDepartment(departmentDto);
        CollegeDto collegeDto =dto.getCollege();
        collegeDto.setCollegeCity(null);
        UniversityDto universityDto = collegeDto.getUniversity();
        universityDto.setUniversityCity(null);
        ZoneDto zoneDto = universityDto.getZone();
        zoneDto.setZoneName(null);
        zoneDto.setState(null);
        collegeDto.setUniversity(universityDto);
        dto.setCollege(collegeDto);
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
                        student.getStudentName()
                        )).collect(Collectors.toList());
        return new PageImpl<>(studentDtoList,  pageable, studentDtoList.size());
    }

    public void getSem(Long sem, CpiDto cpi){
        var semester =Integer.valueOf(sem.intValue());
        switch (semester){
            case 1:
                checkIfSem1CpiIsEqualNull(cpi.getSem1Cpi());
                checkIfSem2CpiIsNotEqualNull(cpi.getSem2Cpi());
                checkIfSem3CpiIsNotEqualNull(cpi.getSem3Cpi());
                checkIfSem4CpiIsNotEqualNull(cpi.getSem4Cpi());
                checkIfSem5CpiIsNotEqualNull(cpi.getSem5Cpi());
                checkIfSem6CpiIsNotEqualNull(cpi.getSem6Cpi());
                checkIfSem7CpiIsNotEqualNull(cpi.getSem7Cpi());
                checkIfSem8CpiIsNotEqualNull(cpi.getSem8Cpi());
                break;
            case 2:
                checkIfSem1CpiIsEqualNull(cpi.getSem1Cpi());
                checkIfSem2CpiIsEqualNull(cpi.getSem2Cpi());
                checkIfSem3CpiIsNotEqualNull(cpi.getSem3Cpi());
                checkIfSem4CpiIsNotEqualNull(cpi.getSem4Cpi());
                checkIfSem5CpiIsNotEqualNull(cpi.getSem5Cpi());
                checkIfSem6CpiIsNotEqualNull(cpi.getSem6Cpi());
                checkIfSem7CpiIsNotEqualNull(cpi.getSem7Cpi());
                checkIfSem8CpiIsNotEqualNull(cpi.getSem8Cpi());
                break;
            case 3:
                checkIfSem1CpiIsEqualNull(cpi.getSem1Cpi());
                checkIfSem2CpiIsEqualNull(cpi.getSem2Cpi());
                checkIfSem3CpiIsEqualNull(cpi.getSem3Cpi());
                checkIfSem4CpiIsNotEqualNull(cpi.getSem4Cpi());
                checkIfSem5CpiIsNotEqualNull(cpi.getSem5Cpi());
                checkIfSem6CpiIsNotEqualNull(cpi.getSem6Cpi());
                checkIfSem7CpiIsNotEqualNull(cpi.getSem7Cpi());
                checkIfSem8CpiIsNotEqualNull(cpi.getSem8Cpi());

                break;
            case 4:
                checkIfSem1CpiIsEqualNull(cpi.getSem1Cpi());
                checkIfSem2CpiIsEqualNull(cpi.getSem2Cpi());
                checkIfSem3CpiIsEqualNull(cpi.getSem3Cpi());
                checkIfSem4CpiIsEqualNull(cpi.getSem4Cpi());
                checkIfSem5CpiIsNotEqualNull(cpi.getSem5Cpi());
                checkIfSem6CpiIsNotEqualNull(cpi.getSem6Cpi());
                checkIfSem7CpiIsNotEqualNull(cpi.getSem7Cpi());
                checkIfSem8CpiIsNotEqualNull(cpi.getSem8Cpi());
                break;
            case 5:
                checkIfSem1CpiIsEqualNull(cpi.getSem1Cpi());
                checkIfSem2CpiIsEqualNull(cpi.getSem2Cpi());
                checkIfSem3CpiIsEqualNull(cpi.getSem3Cpi());
                checkIfSem4CpiIsEqualNull(cpi.getSem4Cpi());
                checkIfSem5CpiIsEqualNull(cpi.getSem5Cpi());
                checkIfSem6CpiIsNotEqualNull(cpi.getSem6Cpi());
                checkIfSem7CpiIsNotEqualNull(cpi.getSem7Cpi());
                checkIfSem8CpiIsNotEqualNull(cpi.getSem8Cpi());
                break;
            case 6:
                checkIfSem1CpiIsEqualNull(cpi.getSem1Cpi());
                checkIfSem2CpiIsEqualNull(cpi.getSem2Cpi());
                checkIfSem3CpiIsEqualNull(cpi.getSem3Cpi());
                checkIfSem4CpiIsEqualNull(cpi.getSem4Cpi());
                checkIfSem5CpiIsEqualNull(cpi.getSem5Cpi());
                checkIfSem6CpiIsEqualNull(cpi.getSem6Cpi());
                checkIfSem7CpiIsNotEqualNull(cpi.getSem7Cpi());
                checkIfSem8CpiIsNotEqualNull(cpi.getSem8Cpi());
                break;
            case 7:
                checkIfSem1CpiIsEqualNull(cpi.getSem1Cpi());
                checkIfSem2CpiIsEqualNull(cpi.getSem2Cpi());
                checkIfSem3CpiIsEqualNull(cpi.getSem3Cpi());
                checkIfSem4CpiIsEqualNull(cpi.getSem4Cpi());
                checkIfSem5CpiIsEqualNull(cpi.getSem5Cpi());
                checkIfSem6CpiIsEqualNull(cpi.getSem6Cpi());
                checkIfSem7CpiIsEqualNull(cpi.getSem7Cpi());
                checkIfSem8CpiIsNotEqualNull(cpi.getSem8Cpi());
                break;
            default:
                checkIfSem1CpiIsEqualNull(cpi.getSem1Cpi());
                checkIfSem2CpiIsEqualNull(cpi.getSem2Cpi());
                checkIfSem3CpiIsEqualNull(cpi.getSem3Cpi());
                checkIfSem4CpiIsEqualNull(cpi.getSem4Cpi());
                checkIfSem5CpiIsEqualNull(cpi.getSem5Cpi());
                checkIfSem6CpiIsEqualNull(cpi.getSem6Cpi());
                checkIfSem7CpiIsEqualNull(cpi.getSem7Cpi());
                checkIfSem8CpiIsEqualNull(cpi.getSem8Cpi());
                break;

        }
    }

    public void checkIfSem1CpiIsEqualNull(Float sem1Cpi){
        if(sem1Cpi == null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Please enter sem1Cpi.");
        }
    }

    public void checkIfSem2CpiIsEqualNull(Float sem2Cpi){
        if(sem2Cpi == null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Please enter sem2Cpi.");
        }
    }

    public void checkIfSem3CpiIsEqualNull(Float sem3Cpi){
        if(sem3Cpi == null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Please enter sem3Cpi.");
        }
    }

    public void checkIfSem4CpiIsEqualNull(Float sem4Cpi){
        if(sem4Cpi == null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Please enter sem4Cpi.");
        }
    }

    public void checkIfSem5CpiIsEqualNull(Float sem5Cpi){
        if(sem5Cpi == null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Please enter sem5Cpi.");
        }
    }

    public void checkIfSem6CpiIsEqualNull(Float sem6Cpi){
        if(sem6Cpi == null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Please enter sem6Cpi.");
        }
    }

    public void checkIfSem7CpiIsEqualNull(Float sem7Cpi){
        if(sem7Cpi == null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Please enter sem7Cpi.");
        }
    }

    public void checkIfSem8CpiIsEqualNull(Float sem8Cpi){
        if(sem8Cpi == null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Please enter sem8Cpi.");
        }
    }

    public void checkIfSem2CpiIsNotEqualNull(Float sem2Cpi){
        if(sem2Cpi != null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Can't enter sem2Cpi, Please check semester of student.");
        }
    }

    public void checkIfSem3CpiIsNotEqualNull(Float sem3Cpi){
        if(sem3Cpi != null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Can't enter sem3Cpi, Please check semester of student.");
        }
    }

    public void checkIfSem4CpiIsNotEqualNull(Float sem4Cpi){
        if(sem4Cpi != null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Can't enter sem4Cpi, Please check semester of student.");
        }
    }

    public void checkIfSem5CpiIsNotEqualNull(Float sem5Cpi){
        if(sem5Cpi != null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Can't enter sem5Cpi, Please check semester of student.");
        }
    }

    public void checkIfSem6CpiIsNotEqualNull(Float sem6Cpi){
        if(sem6Cpi != null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Can't enter sem6Cpi, Please check semester of student.");
        }
    }

    public void checkIfSem7CpiIsNotEqualNull(Float sem7Cpi){
        if(sem7Cpi != null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Can't enter sem7Cpi, Please check semester of student.");
        }
    }

    public void checkIfSem8CpiIsNotEqualNull(Float sem8Cpi){
        if(sem8Cpi != null){
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Can't enter sem8Cpi, Please check semester of student.");
        }
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

}






/*
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

 */






/*
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
//                student.setCpi(studentDto.getCpi());
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
 */