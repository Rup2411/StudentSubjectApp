package com.student.subject.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_entity")
public class UserEntity implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "email")
	private String emailId;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "contact_number")
	private String contactNumber;
	
	@Column(name = "user_role")
	private String userRole;
	
	@ElementCollection
	@Column(name = "enrolled_subject")
	private Set<String> enrollSubjectCodes = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(userRole));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return this.emailId;
	}
	public boolean isAccountNonExpired() 
	{
		return true;
	}
	@Override 
	public boolean isAccountNonLocked() 
	{
		return true;
	}
	@Override 
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override 
	public boolean isEnabled() 
	{
		return true;
	}
}
