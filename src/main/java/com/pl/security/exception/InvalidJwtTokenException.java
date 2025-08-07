package com.pl.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@SuppressWarnings("serial")
public class InvalidJwtTokenException extends RuntimeException {
    public InvalidJwtTokenException(String message) {
        super(message);
    }
    
    public InvalidJwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}