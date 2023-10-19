package com.mazagao.mazagao.execeptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthentication extends AuthenticationException{

    private static final long serialVersionUID = 1L;

    public InvalidJwtAuthentication(String ex) {
        super(ex);
    }
}