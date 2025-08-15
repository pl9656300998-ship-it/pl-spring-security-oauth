package com.pl.security.service.impl;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.pl.security.dto.ResponseBody;
import com.pl.security.dto.UsermasterDTO;
import com.pl.security.model.JwtToken;
import com.pl.security.model.UserMaster;
import com.pl.security.repo.IJwtTokenRepo;
import com.pl.security.service.IJWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JWTServiceImpl implements IJWTService {

	private String secretKey = "c27ktI6LTB+C6aMZfjMb75brxmOyw+xLSym4XifFsnI=";

	@Autowired
	private IJwtTokenRepo tokenRepo;

	@Override
	public String generateToken(UserMaster user,HttpServletRequest request)  throws Exception{
		
	
		Map<String, Object> additionalinfo = new LinkedHashMap<>();
		additionalinfo.put("username", user.getUsername());
		additionalinfo.put("role", user.getRole().getRoleName());
		additionalinfo.put("roleId", user.getRole().getId());
		additionalinfo.put("name", user.getFullName());
		additionalinfo.put("staffId", user.getStaffId());
		additionalinfo.put("email", user.getEmail());
		additionalinfo.put("mobile", user.getMobile());
		additionalinfo.put("tenant", user.getTenant());
		additionalinfo.put("host", request.getRemoteHost());
		additionalinfo.put("hostIp", request.getRemoteAddr());
		additionalinfo.put("userAgent", request.getHeader("User-Agent"));
		additionalinfo.put("sessionId", request.getSession().getId());
		

		return Jwts.builder().setSubject(user.getUsername()).setClaims(additionalinfo)
				.setIssuedAt(new Date(System.currentTimeMillis()))
			
				.signWith(getSignatureKey(), SignatureAlgorithm.HS256).compact();
	
	}

	@Override
	public String generateRefreshToken(Map<String, Object> extraClaims, UserMaster user,HttpServletRequest request)  throws Exception {

		
		Map<String, Object> additionalinfo = new LinkedHashMap<>();
		additionalinfo.put("username", user.getUsername());
		additionalinfo.put("role", user.getRole().getRoleName());
		additionalinfo.put("roleId", user.getRole().getId());
		additionalinfo.put("name", user.getFullName());
		additionalinfo.put("staffId", user.getStaffId());
		additionalinfo.put("email", user.getEmail());
		additionalinfo.put("mobile", user.getMobile());
		additionalinfo.put("tenant", user.getTenant());
		additionalinfo.put("host", request.getRemoteHost());
		additionalinfo.put("hostIp", request.getRemoteAddr());
		additionalinfo.put("userAgent", request.getHeader("User-Agent"));
		additionalinfo.put("sessionId", request.getSession().getId());

	
		return Jwts.builder().setClaims(additionalinfo).setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
		
				.signWith(getSignatureKey(), SignatureAlgorithm.HS256).compact();

	}

	private Key getSignatureKey() {
	
		byte[] key = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(key);

	}
	


	@Override
	public String extractUsername(String token) {
		Claims c = Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(token).getBody();
		return (String) c.get("username");
	}
	
	@Override
	public String getUsername(String token) {
		
		String tok = "";
		if (token.split("\\s++").length == 2) {
			tok = token.split("\\s++")[1];
		} else {
			tok = token;
		}
		Claims c = Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(tok).getBody();
		return (String) c.get("username");
	}
	
	@Override
	public Integer getApplicationOwnerId(String token) {
		
		String tok = "";
		if (token.split("\\s++").length == 2) {
			tok = token.split("\\s++")[1];
		} else {
			tok = token;
		}
		Claims c = Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(tok).getBody();
		return  (Integer)c.get("applicationOwnerId");
	}

	@Override
	public Boolean isTokenValid(String jwtToken, UserDetails userDetails) 
	{
		
		return tokenRepo.findByToken(jwtToken)
        .map(tok -> tok.getActive() && tok.getExpiresAt().isAfter(LocalDateTime.now()))
        .orElse(false);

	}
	
	@Override
	public Boolean isRefreshTokenValid(String token)
	{
		
		return tokenRepo.findByRefreshToken(token)
        .map(tok -> tok.getActive() && tok.getRefreshExpiresAt().isAfter(LocalDateTime.now()))
        .orElse(false);

	}


	@Override
	public String getTokenValidity(String username) {

		return null;
	}

	@Override
	public Boolean clearToken(String token) {


		return null;
	}

	@Override
	public ResponseEntity<ResponseBody> verify(String token) throws SignatureException{
	
			String tok = "";
			if (token.split("\\s++").length == 2) {
				tok = token.split("\\s++")[1];
			} else {
				tok = token;
			}
			
			return new ResponseEntity<>(new ResponseBody(200,  "Token Verified",Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(tok).getBody()), HttpStatus.OK);

		
	}

	@Override
	public ResponseEntity<ResponseBody> logout(String token) throws Exception{
		try {
			String tokenstring = token.split(" ")[1].trim();
			Optional<JwtToken> jwttoken = tokenRepo.findByToken(tokenstring);
			if(jwttoken.isEmpty())
			{
				return new ResponseEntity<>(new ResponseBody(401,"Invalid Token",null),HttpStatus.UNAUTHORIZED);
			}
			else
			{
				//jwttoken.get().setActive("N");
				tokenRepo.delete(jwttoken.get());
				return new ResponseEntity<>(new ResponseBody(200,"Logout Success",null),HttpStatus.OK);	
			}
		
		} catch (Exception e) {
			
			return new ResponseEntity<>(new ResponseBody(401,"Invalid Token",null),HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public Boolean checkFromScope(String token, String scope) {
//		String tok = "";
//		if (token.split("\\s++").length == 2) {
//			tok = token.split("\\s++")[1];
//		} else {
//			tok = token;
//		}
//		Claims c = Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(tok).getBody();
//		ObjectMapper objectMapper = new ObjectMapper();
//		List<UserScopeDTO> dto = objectMapper.convertValue(c.get("scope"), new TypeReference<List<UserScopeDTO>>() {});
//		return dto.stream().anyMatch(s -> s.getDescription().equals(scope));
		
		return null;
		
	}

	@Override
	public UsermasterDTO getUserDetails(String token) {
		String tok = "";
		if (token.split("\\s++").length == 2) {
			tok = token.split("\\s++")[1];
		} else {
			tok = token;
		}
		Claims c = Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(tok).getBody();
		String username =  (String) c.get("username");
		String staffId =  (String) c.get("staffId");
		String role = (String)c.get("role");
		Integer roleid = (Integer)c.get("roleId");
		Integer tenant = (Integer)c.get("tenant");
		UsermasterDTO responseDto = new UsermasterDTO();
		responseDto.setUsername(username);
		responseDto.setStaffId(staffId);
		responseDto.setRole(role);
		responseDto.setRoleId(roleid);
		responseDto.setTenant(tenant);
		return responseDto;

	}

	@Override
	public ResponseEntity<ResponseBody> checkToken(String token) {
		
		String jwtToken = "";
		if (token.split("\\s++").length == 2) {
			jwtToken = token.split("\\s++")[1];
			System.out.println("TOKEN HAVE SPACE");
		} else {
			jwtToken = token;
		}
		System.out.println("TOKEN = "+token);
		Optional<JwtToken> jwttoken = tokenRepo.findByToken(token);
		System.out.println("TOKEN OBJ = "+jwttoken.get());
		Boolean valid = tokenRepo.findByToken(jwtToken)
		        .map(tok -> tok.getActive() && tok.getExpiresAt().isAfter(LocalDateTime.now()))
		        .orElse(false);
		
		if(valid)
		{
			return new ResponseEntity<>(new ResponseBody(200,"Token is valid",null),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(new ResponseBody(401,"Invalid Token",null),HttpStatus.UNAUTHORIZED);
		}
	}
	

}
