package com.example.RoleBasedJwtAuthentication.Service;

import com.example.RoleBasedJwtAuthentication.Dto.PrincipalDto;
import org.springframework.data.domain.Page;

public interface PrincipalService {
    PrincipalDto addPrincipal(PrincipalDto principalDto);

    PrincipalDto getPrincipalByPrincipalId(String principalId);

    Page<PrincipalDto> getAllPrincipals(int pageNo);
}
