package com.pl.security.service.impl;

import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pl.security.model.UserMaster;
import com.pl.security.repo.IUserMasterRepo;
import com.pl.security.service.IJWTService;
import com.pl.security.service.IUserMasterService;

@Service
public class UsermasterServiceImpl implements IUserMasterService{

	@Autowired
	private IUserMasterRepo repo;
	
	@Autowired
	private IJWTService jwtService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@Override
	public UserMaster findByUsername(String username) {

		return repo.findByUsername(username).get();
	}
	
	@Override
	public ResponseEntity<?> save(UserMaster dto, String token) throws Exception {

		String createdBy = jwtService.getUserDetails(token).getStaffId();

		UserMaster newUser = new UserMaster();
		newUser.setStaffId(dto.getStaffId());
		newUser.setAccountNonExpired(true);
		newUser.setAccountNonLocked(true);
		newUser.setActive(true);
		newUser.setCredentialsNonExpired(true);
		newUser.setPasswordReset(true);
		newUser.setEmail(dto.getEmail());
		newUser.setEnabled("1");
		newUser.setRoleId(dto.getRoleId());
		newUser.setFullName(dto.getFullName());
		newUser.setMobile(dto.getMobile());
		newUser.setUsername(dto.getUsername());
		newUser.setPassword(getEncodedPass(dto.getPassword()));
		newUser.setEnteredBy(createdBy);
		newUser.setEnteredDt(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(repo.save(newUser),HttpStatus.OK);
	
		

	}
	
	public String getEncodedPass(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public UserDetailsService userDetailsService() {

		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

				return repo.findByUsername(username).get();
						
			}
		};
	}

	@Override
	public UserMaster edit(UserMaster usermaster, String token) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserMaster findByUsernameAndPassword(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
