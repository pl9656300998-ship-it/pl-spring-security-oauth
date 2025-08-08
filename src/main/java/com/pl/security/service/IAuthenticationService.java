package com.pl.security.service;

import org.springframework.http.ResponseEntity;
import com.pl.security.dto.LoginDTO;
import com.pl.security.dto.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationService {

	public ResponseEntity<ResponseBody> login(LoginDTO dto,HttpServletRequest request) throws Exception;
	
	public ResponseEntity<ResponseBody> refreshToken(LoginDTO token,HttpServletRequest request) throws Exception;
	
	public ResponseEntity<ResponseBody> logout(String token) throws Exception;


}
