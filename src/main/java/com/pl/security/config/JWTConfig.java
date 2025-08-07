package com.pl.security.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import com.pl.security.service.IJWTService;
import com.pl.security.service.impl.JWTServiceImpl;

@Configurable
public class JWTConfig {

	@Bean
    public IJWTService jwtService() {
        return new JWTServiceImpl();
    }
}
