package com.pl.security.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pl.security.model.UserMaster;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponseDTO {
	
	private String accessToken;
	private String refreshToken;
	private Integer validity;
	private UserMaster user;

}
