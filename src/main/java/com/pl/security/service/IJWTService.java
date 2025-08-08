package com.pl.security.service;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import com.pl.security.dto.ResponseBody;
import com.pl.security.dto.UsermasterDTO;
import com.pl.security.model.UserMaster;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;

public interface IJWTService {
	
	public String extractUsername(String token);
	
	public String getUsername(String token);
	
	public UsermasterDTO getUserDetails(String token);
	
	public Integer getApplicationOwnerId(String token);
	
	public String getTokenValidity(String username);

	public String generateToken(UserMaster userDetails,HttpServletRequest request) throws Exception;
	
	public String generateRefreshToken(Map<String, Object> extraClaims,UserMaster userDetails,HttpServletRequest request) throws Exception;
	
	public Boolean isTokenValid(String token, UserDetails userDetails);
	
	public Boolean clearToken(String token);

	public ResponseEntity<ResponseBody> logout(String token) throws Exception;
	
	public ResponseEntity<ResponseBody> verify(String token) throws SignatureException;
	
	public Boolean isRefreshTokenValid(String token); 
	
	public Boolean checkFromScope(String token, String scope); 
	
	public ResponseEntity<ResponseBody> checkToken(String token);
	
	

	
}
