package com.pl.security.dto;
/**
* @author jayasankar.47@gmail.com
*/
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDTO {
	
	private String username;
	
	private String password;
	
	private String staffId;
	
	private String token;
	
	private Integer tenant;

}
