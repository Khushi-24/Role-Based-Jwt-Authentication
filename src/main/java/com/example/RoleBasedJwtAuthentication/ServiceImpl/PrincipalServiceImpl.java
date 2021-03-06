package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityAlreadyExistsException;
import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDto;
import com.example.RoleBasedJwtAuthentication.Dto.PrincipalDto;
import com.example.RoleBasedJwtAuthentication.Dto.UniversityDto;
import com.example.RoleBasedJwtAuthentication.Dto.ZoneDto;
import com.example.RoleBasedJwtAuthentication.Entity.Principal;
import com.example.RoleBasedJwtAuthentication.Entity.User;
import com.example.RoleBasedJwtAuthentication.Repository.CollegeRepository;
import com.example.RoleBasedJwtAuthentication.Repository.PrincipalRepository;
import com.example.RoleBasedJwtAuthentication.Repository.UserRepository;
import com.example.RoleBasedJwtAuthentication.Service.PrincipalService;
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
public class PrincipalServiceImpl implements PrincipalService {

    private final PrincipalRepository principalRepository;
    private final UserRepository userRepository;
    private final CollegeRepository collegeRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder;

    @Override
    public PrincipalDto addPrincipal(PrincipalDto principalDto) {
        if(!principalRepository.existsById(principalDto.getPrincipalId())){
            if(collegeRepository.existsById(principalDto.getCollege().getCollegeId())){
                if(principalRepository.existsByCollegeCollegeId(principalDto.getCollege().getCollegeId())){
                    throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "One college can have only one Principal.");
                }else{
                    Principal principal = new Principal();
                    User user = new User();
                    modelMapper.getConfiguration().setAmbiguityIgnored(true);
                    modelMapper.map(principalDto, principal);
                    principal.setPrincipalPassword(getEncodedPassword(principalDto.getPrincipalPassword()));
                    if(!userRepository.existsById(principal.getPrincipalId())){
                        user.setUserName(principal.getPrincipalId());
                        user.setUserPassword(principal.getPrincipalPassword());
                        user.setUserRole("Principal");
                        userRepository.save(user);
                        principalRepository.save(principal);
                    }else{
                        throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "Principal id is not available.");
                    }
                    return principalDto;
                }
            }else{
                throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "Please enter valid college id, college id doesn't exists.");
            }
        }else{
            throw new EntityAlreadyExistsException(HttpStatus.CONFLICT, "Principal Already Exists.");
        }

    }

    @Override
    public PrincipalDto getPrincipalByPrincipalId(String principalId) {
        Principal principal = principalRepository.findById(principalId).orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND ,"Principal does not exist."));
        PrincipalDto principalDto = new PrincipalDto();
        modelMapper.map(principal, principalDto);
        principalDto.setPrincipalPassword(null);
        CollegeDto dto = principalDto.getCollege();
        dto.setCollegeCity(null);
        UniversityDto universityDto = dto.getUniversity();
        universityDto.setUniversityCity(null);
        ZoneDto zoneDto = universityDto.getZone();
        zoneDto.setZoneName(null);
        zoneDto.setState(null);
        universityDto.setZone(zoneDto);
        dto.setUniversity(universityDto);
        principalDto.setCollege(dto);
        principalDto.setCountOfStudents(principalRepository.countStudentByPrincipalId(principalId));
        return principalDto;
    }

    @Override
    public Page<PrincipalDto> getAllPrincipals(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<Principal> principals = principalRepository.findAll(pageable);
        List<PrincipalDto> principalDtoList = principals.stream().map((Principal principal) ->
                new PrincipalDto(
                        principal.getPrincipalId(),
                        principal.getPrincipalName()
                        )).collect(Collectors.toList());
        return new PageImpl<>(principalDtoList,  pageable, principalDtoList.size());
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
