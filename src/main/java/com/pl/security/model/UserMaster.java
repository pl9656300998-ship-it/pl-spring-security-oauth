package com.pl.security.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_master")
public class UserMaster implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = true,updatable = false)
	private Integer id;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "role_id")
	private Integer roleId;
	
	@JoinColumn(name ="role_id", insertable = false, updatable = false)
	@ManyToOne(targetEntity=Role.class, fetch =FetchType.EAGER)
	private Role role ;
	
	@Column(name = "staff_id")
	private String staffId;
	
	@Column(name = "account_non_expired", insertable = true,updatable = true)
	private Boolean accountNonExpired;
	
	@Column(name = "account_non_locked", insertable = true,updatable = true)
	private Boolean accountNonLocked;
	
	@Column(name = "credentials_non_expired", insertable = true,updatable = true)
	private Boolean credentialsNonExpired;
	
	@Column(name = "password_reset", insertable = true,updatable = true)
	private Boolean passwordReset;
	
	@Column(name = "ENABLED", insertable = true,updatable = true)
	private String enabled;
	
	@Column(name = "ACTIVE")
	private Boolean active;
	
	@Column(name = "tenant")
	private Integer tenant;
	
	@JsonFormat(pattern = "dd-MM-yyyy | HH:mm:ss",timezone = "Asia/Kolkata")
	@Column(name = "ENTERED_DT")
	private Timestamp enteredDt;
	
	@Column(name = "ENTERED_BY")
	private String enteredBy;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		List<Role> roleList = new ArrayList<>();
		if(role!=null)
		{	
			roleList.add(role);
			roleList.forEach(r ->{
				authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
			});
		}

		return authorities;
	}

}