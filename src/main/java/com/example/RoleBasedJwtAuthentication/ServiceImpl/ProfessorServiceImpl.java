package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDto;
import com.example.RoleBasedJwtAuthentication.Dto.ProfessorDepartmentDto;
import com.example.RoleBasedJwtAuthentication.Dto.ProfessorDto;
import com.example.RoleBasedJwtAuthentication.Entity.*;
import com.example.RoleBasedJwtAuthentication.Repository.DepartmentRepository;
import com.example.RoleBasedJwtAuthentication.Repository.ProfessorDepartmentRepository;
import com.example.RoleBasedJwtAuthentication.Repository.ProfessorRepository;
import com.example.RoleBasedJwtAuthentication.Repository.UserRepository;
import com.example.RoleBasedJwtAuthentication.Service.ProfessorService;
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

    @Override
    public ProfessorDto getProfessorById(String professorId) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new javax.persistence.EntityNotFoundException("Professor does not exist."));
        ProfessorDto professorDto = new ProfessorDto();
        modelMapper.map(professor, professorDto);
        professorDto.setCountOfDepartment(professorDepartmentRepository.countByProfessorProfessorId(professorId));
        professorDto.setCountOfColleges(professorDepartmentRepository.countOfCollegesByProfessorId(professorId));
        professorDto.setCountOfUniversity(professorDepartmentRepository.countOfUniversitiesByProfessorId(professorId));
        professorDto.setCountOfZones(professorDepartmentRepository.countOfZonesByProfessorId(professorId));
        professorDto.setProfessorPassword(null);
        return professorDto;
    }

    @Override
    public Page<ProfessorDto> getAllProfessor(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<Professor> professors = professorRepository.findAll(pageable);
        List<ProfessorDto> professorDtoList = professors.stream().map((Professor professor) ->
                new ProfessorDto(
                        professor.getProfessorId(),
                        professor.getProfessorName()
                        )).collect(Collectors.toList());
        return new PageImpl<>(professorDtoList,  pageable, professorDtoList.size());
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
