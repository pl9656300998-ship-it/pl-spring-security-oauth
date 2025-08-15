package com.pl.security.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role_master")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",insertable = false,updatable = true)
	private Integer id;
	
	@Column(name = "description", insertable = true,updatable = true)
	private String description;
	
	@Column(name = "role_name", insertable = true,updatable = true)
	private String roleName;
	
	@Column(name = "tenant", insertable = true,updatable = true)
	private Integer tenant;
	
	@Column(name = "active", insertable = true,updatable = true)
	private Boolean active;
	
	@Column(name = "entered_by", insertable = true,updatable = true)
	private String enteredBy;
	
	@JsonFormat(pattern = "dd-MM-yyyy | HH:mm:ss",timezone = "Asia/Kolkata")
	@Column(name = "entered_dt", insertable = true,updatable = true)
	private Timestamp enteredDt;
}