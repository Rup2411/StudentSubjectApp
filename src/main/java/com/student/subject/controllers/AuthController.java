package com.student.subject.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.subject.dtos.UserDto;
import com.student.subject.exceptions.CustomException;
import com.student.subject.jwtUtils.JwtRequestDto;
import com.student.subject.jwtUtils.JwtResponseDto;
import com.student.subject.jwtUtils.JwtTokenHelper;
import com.student.subject.services.UserService;

import jakarta.validation.Valid;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
	
	@Autowired
	UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtTokenHelper jwtTokenHelper;
	
	@PostMapping("register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto dto){
		
		try {
			UserDto userDto = userService.createUser(dto);
			
			return new ResponseEntity<>(userDto, HttpStatus.CREATED);
		} catch (CustomException e) {
			Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", e.getMessage());
	        
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody JwtRequestDto dto){
		this.doAuthenticate(dto.getEmail(), dto.getPassword());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
		
		UserDto entity = userService.getUserByEmail(dto.getEmail());
		
		String token = jwtTokenHelper.generateToken(userDetails, entity.getUserRole(), userDetails.getUsername(), entity.getContactNumber());
		
		JwtResponseDto response = JwtResponseDto.builder().jwtToken(token).username(userDetails.getUsername()).build();
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	 private void doAuthenticate(String email, String password) throws BadCredentialsException {
	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
	        try {
	        	authenticationManager.authenticate(authentication);
	        } catch (BadCredentialsException | UsernameNotFoundException e) {
	            throw new BadCredentialsException("Invalid Username or Password");
	        } catch (Exception e) {
	        	throw new BadCredentialsException("Invalid Credentials");
			}
	    }
}

