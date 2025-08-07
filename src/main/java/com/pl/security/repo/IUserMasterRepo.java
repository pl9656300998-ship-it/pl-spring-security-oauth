package com.pl.security.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pl.security.model.UserMaster;

@Repository
public interface IUserMasterRepo extends JpaRepository<UserMaster, Integer>{
	
	public Optional<UserMaster> findByUsername(String username);
	
	public Optional<UserMaster> findByUsernameAndPassword(String username, String password);
	
}
