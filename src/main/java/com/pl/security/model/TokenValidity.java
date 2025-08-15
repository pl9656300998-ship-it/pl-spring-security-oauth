package com.pl.security.model;

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
@Table(name = "token_validity")
public class TokenValidity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",insertable = false,updatable = true)
	private Integer id;
	
	@Column(name = "token_validity", insertable = true,updatable = true)
	private Integer validity;
	
	@Column(name = "tenant", insertable = true,updatable = true)
	private Integer tenant;
	
	
}