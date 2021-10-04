package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.ProfessorDepartmentDto;
import com.example.RoleBasedJwtAuthentication.Dto.ProfessorDto;
import com.example.RoleBasedJwtAuthentication.Entity.Department;
import com.example.RoleBasedJwtAuthentication.Entity.Professor;
import com.example.RoleBasedJwtAuthentication.Entity.ProfessorDepartment;
import com.example.RoleBasedJwtAuthentication.Entity.User;
import com.example.RoleBasedJwtAuthentication.Repository.DepartmentRepository;
import com.example.RoleBasedJwtAuthentication.Repository.ProfessorDepartmentRepository;
import com.example.RoleBasedJwtAuthentication.Repository.ProfessorRepository;
import com.example.RoleBasedJwtAuthentication.Repository.UserRepository;
import com.example.RoleBasedJwtAuthentication.Service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final ProfessorDepartmentRepository professorDepartmentRepository;

    private final ModelMapper modelMapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder;


    @Override
    public ProfessorDto addProfessor(ProfessorDto professorDto) {

        if(!professorRepository.existsById(professorDto.getProfessorId())){
            Professor professor= new Professor();
            User user = new User();
            modelMapper.map(professorDto, professor);
            professor.setProfessorPassword(getEncodedPassword(professorDto.getProfessorPassword()));
            professorRepository.save(professor);
            user.setUserName(professorDto.getProfessorId());
            user.setUserPassword(professor.getProfessorPassword());
            user.setUserRole("Teacher");
            userRepository.save(user);
            professorDto.setProfessorPassword("Null");
            return professorDto;
        }else{
            throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "Professor already exists.");
        }
    }

    @Override
    public void addProfessorToDepartment(ProfessorDepartmentDto professorDepartmentDto) {
        Professor professor = professorRepository.findById(professorDepartmentDto.getProfessorId()).orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND, "Professor doesn't exists."));
        Department department = departmentRepository.findById(professorDepartmentDto.getDepartmentId()).orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND, "Department doesn't exists"));
        ProfessorDepartment professorDepartment = new ProfessorDepartment(professor, department);
        if(!professorDepartmentRepository.existsByProfessorAndDepartment(professor, department)){
            professorDepartmentRepository.save(professorDepartment);
        }else{
            throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "Professor already exists in department");
        }

    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
