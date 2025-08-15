package com.pl.security.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jwt_token")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",insertable = false,updatable = true)
	private Integer id;

    @Column(name = "access_token")
    private String token;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "username", length = 255)
    private String userName;
    
    @Column(name = "tenant")
    private Integer tenant;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
    
    @Column(name = "refresh_expires_at", nullable = false)
    private LocalDateTime refreshExpiresAt;

    @Column(name = "active")
    private Boolean active;

	public JwtToken(String token, String refreshToken, String userName, LocalDateTime issuedAt,
			LocalDateTime expiresAt, LocalDateTime refreshExpiresAt, Boolean active, Integer tenant) {
		
		this.token = token;
		this.refreshToken = refreshToken;
		this.userName = userName;
		this.issuedAt = issuedAt;
		this.expiresAt = expiresAt;
		this.refreshExpiresAt = refreshExpiresAt;
		this.active = active;
		this.tenant = tenant;
	}



	public JwtToken(Integer id, String token, String refreshToken, String userName, LocalDateTime issuedAt,
			LocalDateTime expiresAt, LocalDateTime refreshExpiresAt, Boolean active, Integer tenant) {
		this.id = id;
		this.token = token;
		this.refreshToken = refreshToken;
		this.userName = userName;
		this.issuedAt = issuedAt;
		this.expiresAt = expiresAt;
		this.refreshExpiresAt = refreshExpiresAt;
		this.active = active;
		this.tenant = tenant;
	}
	
	
	
    
    


}
