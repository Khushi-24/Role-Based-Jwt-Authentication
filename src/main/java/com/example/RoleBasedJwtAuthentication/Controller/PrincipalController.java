package com.example.RoleBasedJwtAuthentication.Controller;

import com.example.RoleBasedJwtAuthentication.CustomException.EntityNotFoundException;
import com.example.RoleBasedJwtAuthentication.Dto.CollegeDto;
import com.example.RoleBasedJwtAuthentication.Dto.PrincipalDto;
import com.example.RoleBasedJwtAuthentication.Service.PrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PrincipalController {

    private final PrincipalService principalService;

    @PostMapping("/addPrincipal")
    @PreAuthorize("hasRole('Principal')")
    public ResponseEntity<?> addPrincipal(@RequestBody PrincipalDto principalDto){
        PrincipalDto dto = principalService.addPrincipal(principalDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getPrincipalByPrincipalId/{principalId}")
    @PreAuthorize("hasRole('Principal')")
    public ResponseEntity<?> getPrincipalByPrincipalId(@PathVariable String principalId){
        PrincipalDto dto = principalService.getPrincipalByPrincipalId(principalId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getAllPrincipals/{pageNo}")
    @PreAuthorize("hasAnyRole('Teacher','Principal','student')")
    public ResponseEntity<?> getAllPrincipals(@PathVariable(value = "pageNo") int pageNo){
        Page<PrincipalDto> page = principalService.getAllPrincipals(pageNo);
        List<PrincipalDto> collegeDtoList = page.getContent();
        if(pageNo > page.getTotalPages()){
            throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "No further pages available.");
        }
        return new ResponseEntity<>(collegeDtoList, HttpStatus.OK);
    }
}
