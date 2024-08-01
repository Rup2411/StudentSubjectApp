package com.student.subject.jwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.student.subject.entities.UserEntity;
import com.student.subject.exceptions.CustomException;
import com.student.subject.repos.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity userEntity = this.userRepo.findByEmail(username)
		.orElseThrow(() -> new CustomException("User Not Found For Email : " + username));
		
		return userEntity;
	}
}
