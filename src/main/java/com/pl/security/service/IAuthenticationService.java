package com.pl.security.service;

import org.springframework.http.ResponseEntity;
import com.pl.security.dto.LoginDTO;
import com.pl.security.dto.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationService {

	public ResponseEntity<ResponseDTO> login(LoginDTO dto,HttpServletRequest request) throws Exception;
	
	public ResponseEntity<ResponseDTO> refreshToken(LoginDTO token,HttpServletRequest request) throws Exception;
	
	public ResponseEntity<ResponseDTO> logout(String token) throws Exception;


}
