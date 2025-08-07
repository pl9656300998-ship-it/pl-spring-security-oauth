package com.pl.security.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.pl.security.model.UserMaster;

public interface IUserMasterService{
	
	public ResponseEntity<?> save(UserMaster usermaster, String token) throws Exception;
	
	public UserMaster edit(UserMaster usermaster, String token) throws Exception;
	
	public UserMaster findByUsername(String username) throws Exception;
	
	public UserMaster findByUsernameAndPassword(String username,String password) throws Exception;

	public UserDetailsService userDetailsService();

	

}
