package com.pl.security.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pl.security.model.TokenValidity;

public interface ITokenValidityRepo extends JpaRepository<TokenValidity, Integer>{
	
	public Optional<TokenValidity> findByTenant(Integer tenant);

}
