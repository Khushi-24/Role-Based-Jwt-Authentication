package com.example.RoleBasedJwtAuthentication.ServiceImpl;

import com.example.RoleBasedJwtAuthentication.Dto.JwtRequest;
import com.example.RoleBasedJwtAuthentication.Dto.JwtResponse;
import com.example.RoleBasedJwtAuthentication.Entity.Student;
import com.example.RoleBasedJwtAuthentication.Repository.StudentRepository;
import com.example.RoleBasedJwtAuthentication.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
//@RequiredArgsConstructor
public class JwtServiceImpl implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private  AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String studentId = jwtRequest.getStudentID();
        String studentPassword = jwtRequest.getStudentPassword();
        authenticate(studentId, studentPassword);

        UserDetails userDetails = loadUserByUsername(studentId);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

       Student student = studentRepository.findById(studentId).get();
        return new JwtResponse(student, newGeneratedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {
        Student student = studentRepository.findById(studentId).get();

        if (student != null) {
            return new org.springframework.security.core.userdetails.User(
                    student.getStudentId(),
                    student.getStudentPassword(),
                    getAuthority(student)
            );
        } else {
            throw new UsernameNotFoundException("User not found with studentId: " + studentId);
        }
    }

    private Set getAuthority(Student student) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        user.forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + "User"));
//        });
        authorities.add(new SimpleGrantedAuthority("ROLE_" + "User"));
        return authorities;
    }


    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
