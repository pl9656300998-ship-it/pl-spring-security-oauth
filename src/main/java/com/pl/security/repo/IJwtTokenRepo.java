package com.pl.security.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pl.security.model.JwtToken;


@Repository
public interface IJwtTokenRepo extends JpaRepository<JwtToken, Integer>{
	
	Optional<JwtToken> findByToken(String token);
	
	Optional<JwtToken> findByRefreshToken(String refreshToken);
	
	Optional<JwtToken> findByUserName(String userName);
	
	Optional<JwtToken> findByUserNameAndActive(String userName,Boolean active);
	

}
