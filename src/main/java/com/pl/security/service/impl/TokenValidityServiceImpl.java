package com.pl.security.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pl.security.model.TokenValidity;
import com.pl.security.repo.ITokenValidityRepo;
import com.pl.security.service.ITokenValidityService;

@Service
public class TokenValidityServiceImpl implements ITokenValidityService {

	@Autowired
	private ITokenValidityRepo repo;
	@Override
	public Optional<TokenValidity> findByTenent(Integer tenent) throws Exception {
		return repo.findByTenent(tenent);
	}
	@Override
	public TokenValidity save(TokenValidity tokenValidity) throws Exception {
		if(repo.findByTenent(tokenValidity.getTenent()).isEmpty())
		{
			return repo.save(tokenValidity);
		}
		else
		{
			return null;
		}
		
	}
	@Override
	public TokenValidity update(TokenValidity tokenValidity) throws Exception {
		Optional<TokenValidity> savedTokenValidity = repo.findByTenent(tokenValidity.getTenent());
		if(savedTokenValidity.isEmpty())
		{
			return repo.save(tokenValidity);
		}
		else if(savedTokenValidity.get().getId()==tokenValidity.getId())
		{
			return repo.save(tokenValidity);
		}
		else
		{
			return null;
		}
	}

}
