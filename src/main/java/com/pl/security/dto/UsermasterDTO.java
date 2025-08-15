package com.pl.security.dto;

import java.sql.Timestamp;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsermasterDTO {
	
    private UUID id;
	
	private String fullName;

	private String email;

	private String mobile;

	private String username;

	private String password;
	
	private String role;

	private Integer roleId;

	private String staffId;
	
	private Integer tenant;
	
	@JsonFormat(pattern = "dd-MM-yyyy | HH:mm:ss",timezone = "Asia/Kolkata")
	private Timestamp enteredDt;

	private String enteredBy;

	private String remarks;
	
	@JsonFormat(pattern = "dd-MM-yyyy | HH:mm:ss",timezone = "Asia/Kolkata")
	private Timestamp updatedAt;

	private String active;
	

}
