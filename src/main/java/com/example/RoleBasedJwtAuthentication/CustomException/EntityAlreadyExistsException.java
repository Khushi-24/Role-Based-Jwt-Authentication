package com.example.RoleBasedJwtAuthentication.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException(HttpStatus badRequest, String message){
        super(message);
    }
}