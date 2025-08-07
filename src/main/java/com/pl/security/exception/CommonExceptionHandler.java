package com.pl.security.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pl.security.dto.ResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

@RestControllerAdvice
public class CommonExceptionHandler {
	
	@ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ResponseDTO> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>(new ResponseDTO(401,false,"Authorization failed","Invalid username or password or token"), HttpStatus.UNAUTHORIZED);
    }
	
	 @ExceptionHandler(InvalidJwtTokenException.class)
	 @ResponseStatus(HttpStatus.UNAUTHORIZED)
	 public ResponseEntity<ResponseDTO> handleInvalidJwtTokenException(InvalidJwtTokenException ex) {
		 return new ResponseEntity<>(new ResponseDTO(401,false,"Login failed","invalid token"), HttpStatus.UNAUTHORIZED);
	 }

	 @ExceptionHandler(SignatureException.class)
	 @ResponseStatus(HttpStatus.UNAUTHORIZED)
	 public ResponseEntity<ResponseDTO> signatureException(SignatureException ex) {
		 return new ResponseEntity<>(new ResponseDTO(401,false,"Authorization failed",ex.toString()), HttpStatus.UNAUTHORIZED);
	 }
	 
	 @ExceptionHandler(BadCredentialsException.class)
	 @ResponseStatus(HttpStatus.UNAUTHORIZED)
	 public ResponseEntity<ResponseDTO> badCredentialsException(BadCredentialsException ex) {
		 return new ResponseEntity<>(new ResponseDTO(401,false,"Authorization failed",ex.toString()), HttpStatus.UNAUTHORIZED);
	 }
	 
	 @ExceptionHandler(Exception.class)
	 @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	 public ResponseEntity<ResponseDTO> exception(Exception ex) {
		 return new ResponseEntity<>(new ResponseDTO(500,false,"Internal Server Error",ex.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
	 }
	 
	 @ExceptionHandler(AccessDeniedException.class)
	 @ResponseStatus(HttpStatus.FORBIDDEN)
	    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {

		 return new ResponseEntity<>(new ResponseDTO(403,false,"Access Denied",ex.toString()), HttpStatus.FORBIDDEN);
	    }

}
