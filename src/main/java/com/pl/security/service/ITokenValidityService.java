package com.pl.security.service;

import java.util.Optional;
import com.pl.security.model.TokenValidity;

public interface ITokenValidityService {
	
	public Optional<TokenValidity> findByTenent(Integer tenent) throws Exception;
	
	public TokenValidity save(TokenValidity tokenValidity) throws Exception;
	
	public TokenValidity update(TokenValidity tokenValidity) throws Exception;

}
