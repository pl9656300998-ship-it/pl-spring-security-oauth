package com.pl.security.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.pl.security.dto.AuthenticationResponseDTO;
import com.pl.security.dto.LoginDTO;
import com.pl.security.dto.ResponseBody;
import com.pl.security.model.JwtToken;
import com.pl.security.model.UserMaster;
import com.pl.security.repo.IJwtTokenRepo;
import com.pl.security.repo.IUserMasterRepo;
import com.pl.security.service.IAuthenticationService;
import com.pl.security.service.IJWTService;
import com.pl.security.service.ITokenValidityService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

	@Autowired
	private IUserMasterRepo userRepo;

	@Autowired
	private IJWTService jwtService;
	
	@Autowired
	private ITokenValidityService validitySevice;

	@Autowired
	private IJwtTokenRepo tokenRepo;

	private Integer expiry;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public ResponseEntity<ResponseBody> login(LoginDTO dto, HttpServletRequest request) throws Exception {
		try {
			expiry = validitySevice.findByTenent(dto.getTenant()).get().getValidity();
			UserMaster user = userRepo.findByUsername(dto.getUsername()).get();
			Optional<JwtToken> jwtToken = tokenRepo.findByUserName(dto.getUsername());
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
			if (jwtToken.isEmpty()) {
				String accessToken = jwtService.generateToken(user,request);
				System.out.println("PLING 1 :" + accessToken);
				System.out.println("PLING 2a :" + accessToken.length());
				String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user, request);
					AuthenticationResponseDTO rdto = new AuthenticationResponseDTO();
					rdto.setAccessToken(accessToken);
					rdto.setRefreshToken(refreshToken);
					rdto.setValidity(expiry);
					Optional<JwtToken> token = tokenRepo.findByUserName(dto.getUsername());
					JwtToken newToken;

					LocalDateTime ct = LocalDateTime.now();
					LocalDateTime et = ct.plusSeconds(expiry);
					LocalDateTime ret = ct.plusSeconds(expiry + 300);
					if (token.isEmpty()) {
						newToken = new JwtToken(accessToken, refreshToken, dto.getUsername(), ct, et, ret, true,dto.getTenant());
					} else {
						newToken = new JwtToken(token.get().getId(), accessToken, refreshToken, dto.getUsername(), ct,et, ret, true, dto.getTenant());
					}
					tokenRepo.save(newToken);
					return new ResponseEntity<ResponseBody>(new ResponseBody(200, "Login Success", rdto),
							HttpStatus.OK);

				}

				else if (jwtToken.get().getExpiresAt().isBefore(LocalDateTime.now())) {
					authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

					String accessToken = jwtService.generateToken(user,request);
					String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user, request);
					AuthenticationResponseDTO rdto = new AuthenticationResponseDTO();
					rdto.setAccessToken(accessToken);
					rdto.setRefreshToken(refreshToken);
					rdto.setValidity(expiry);
					Optional<JwtToken> token = tokenRepo.findByUserName(dto.getUsername());
					JwtToken newToken;
					LocalDateTime ct = LocalDateTime.now();
					LocalDateTime et = ct.plusSeconds(expiry);
					LocalDateTime ret = ct.plusSeconds(expiry + 300);
					if (token.isEmpty()) {
						newToken = new JwtToken(accessToken, refreshToken, dto.getUsername(), ct, et, ret, true,
								dto.getTenant());
					} else {
						newToken = new JwtToken(token.get().getId(), accessToken, refreshToken, dto.getUsername(), ct,
								et, ret, true, dto.getTenant());
					}
					tokenRepo.save(newToken);
					return new ResponseEntity<ResponseBody>(new ResponseBody(200,"Login Success", rdto),
							HttpStatus.OK);

				} else {
					authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

					String accessToken = jwtService.generateToken(user,request);
					String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user, request);
					AuthenticationResponseDTO rdto = new AuthenticationResponseDTO();
					rdto.setAccessToken(accessToken);
					rdto.setRefreshToken(refreshToken);
					rdto.setValidity(expiry);
					Optional<JwtToken> token = tokenRepo.findByUserName(dto.getUsername());
					JwtToken newToken;
					LocalDateTime ct = LocalDateTime.now();
					LocalDateTime et = ct.plusSeconds(expiry);
					LocalDateTime ret = ct.plusSeconds(expiry + 300);
					if (token.isEmpty()) {
						newToken = new JwtToken(accessToken, refreshToken, dto.getUsername(), ct, et, ret, true,
								dto.getTenant());
					} else {
						newToken = new JwtToken(token.get().getId(), accessToken, refreshToken, dto.getUsername(), ct,
								et, ret, true, dto.getTenant());
					}
					tokenRepo.save(newToken);
					return new ResponseEntity<ResponseBody>(new ResponseBody(200, "Login Success", rdto),
							HttpStatus.OK);
				}
			
		} catch (Exception ex) {
			System.out.println("Error " + ex);
			return new ResponseEntity<ResponseBody>(new ResponseBody(500,  "Error", ex),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<ResponseBody> refreshToken(LoginDTO dto, HttpServletRequest request) throws Exception {

		String jwt = dto.getToken();

		Optional<JwtToken> jwtToken = tokenRepo.findByRefreshToken(jwt);
		String username = jwtToken.get().getUserName();
		Optional<UserMaster> usermaster = userRepo.findByUsername(username);
		expiry = validitySevice.findByTenent(jwtToken.get().getTenant()).get().getValidity();
		if (jwtService.isRefreshTokenValid(jwt)) {

			String accessToken = jwtService.generateToken(usermaster.get(),request);
			String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), usermaster.get(), request);
			AuthenticationResponseDTO rdto = new AuthenticationResponseDTO();
			rdto.setAccessToken(accessToken);
			rdto.setRefreshToken(refreshToken);
			rdto.setValidity(expiry);
			LocalDateTime ct = LocalDateTime.now();
			LocalDateTime et = ct.plusSeconds(expiry);
			LocalDateTime ret = ct.plusSeconds(expiry + 300);
			jwtToken.get().setToken(accessToken);
			jwtToken.get().setRefreshToken(refreshToken);
			jwtToken.get().setIssuedAt(ct);
			jwtToken.get().setExpiresAt(et);
			jwtToken.get().setRefreshExpiresAt(ret);
			jwtToken.get().setActive(true);
			tokenRepo.save(jwtToken.get());
			return new ResponseEntity<ResponseBody>(new ResponseBody(200,  "Token Refresh Success", rdto),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<ResponseBody>(new ResponseBody(401,  "Token Refresh Failed", "Invalid Token"),
					HttpStatus.UNAUTHORIZED);
		}

	}

	@Override
	public ResponseEntity<ResponseBody> logout(String token) throws Exception {
		return jwtService.logout(token);
	}

}
