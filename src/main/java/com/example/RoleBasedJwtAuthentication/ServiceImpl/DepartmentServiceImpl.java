package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.Dto.DepartmentDto;
import com.example.RoleBasedJwtAuthentication.Entity.Department;
import com.example.RoleBasedJwtAuthentication.Repository.CollegeDepartmentRepository;
import com.example.RoleBasedJwtAuthentication.Repository.DepartmentRepository;
import com.example.RoleBasedJwtAuthentication.Repository.ProfessorDepartmentRepository;
import com.example.RoleBasedJwtAuthentication.Service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CollegeDepartmentRepository collegeDepartmentRepository;
    private final ProfessorDepartmentRepository professorDepartmentRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public DepartmentDto addDepartment(DepartmentDto departmentDto) {
        if(!departmentRepository.existsById(departmentDto.getDepartmentId())){
            Department department = new Department();
            modelMapper.map(departmentDto, department);
            departmentRepository.save(department);
            return departmentDto;
        }else{
            throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "Department Already Exists.");
        }
    }

    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {

        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new javax.persistence.EntityNotFoundException("Department does not exist."));
        DepartmentDto departmentDto = new DepartmentDto();
        modelMapper.map(department, departmentDto);
        departmentDto.setCountOfColleges(collegeDepartmentRepository.countByDepartmentDepartmentId(departmentId));
        departmentDto.setCountOfProfessors(professorDepartmentRepository.countByDepartmentDepartmentId(departmentId));
        departmentDto.setCountOfStudents(collegeDepartmentRepository.countOfStudentsByDepartmentId(departmentId));
        departmentDto.setDuration(null);
        return departmentDto;
    }
}
