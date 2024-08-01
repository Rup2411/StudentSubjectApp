package com.student.subject.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.subject.dtos.UserDto;
import com.student.subject.dtos.UserEnrollmentResponseDto;
import com.student.subject.exceptions.CustomException;
import com.student.subject.services.SubjectService;
import com.student.subject.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequestMapping("/api/user")
@RestController
public class UserController {

	
	@Autowired
	UserService userService;
	
	@Autowired
	SubjectService subjectService;
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllStudentUsers(HttpServletRequest request){
		
		try {
			List<UserDto> dto = userService.getAllStudentUsers(request);
			
			return new ResponseEntity<>(dto, HttpStatus.OK);
			
		} catch (Exception e) {
			
			Map<String, String> errorResponse = new HashMap<>();
			
			 errorResponse.put("error", e.getMessage());
		        
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<?> getUserByEmail(@PathVariable String email){
		
		try {
			UserDto dto = userService.getUserByEmail(email);
			
			return new ResponseEntity<>(dto, HttpStatus.OK);
			
		} catch (CustomException e) {
			
			Map<String, String> errorResponse = new HashMap<>();
			
			 errorResponse.put("error", e.getMessage());
		        
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/subject/{email}")
	public ResponseEntity<?> getAllSubjectsEnrolledByUser(@PathVariable String email){
		
		try {
			UserEnrollmentResponseDto dto = userService.getAllSubjectsEnrolledByStudent(email);
			
			return new ResponseEntity<>(dto, HttpStatus.OK);
			
		} catch (CustomException e) {
			
			Map<String, String> errorResponse = new HashMap<>();
			
			 errorResponse.put("error", e.getMessage());
		        
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}
	
}
