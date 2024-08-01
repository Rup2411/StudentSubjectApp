package com.student.subject.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.subject.dtos.RequestEnrollmentDto;
import com.student.subject.dtos.SubjectDto;
import com.student.subject.dtos.SubjectEnrollmentResponseDto;
import com.student.subject.exceptions.CustomException;
import com.student.subject.services.SubjectService;
import com.student.subject.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/subject")
@RestController
public class SubjectController {

	
	@Autowired
	SubjectService subjectService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/add")
	public ResponseEntity<?> createSubject(@RequestBody SubjectDto dto, HttpServletRequest request){
		
		try {
			SubjectDto subjectDto = subjectService.createSubject(dto, request);
			
			return new ResponseEntity<>(subjectDto, HttpStatus.OK);
			
		} catch (CustomException e) {
			
			Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", e.getMessage());
	        
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
 	@PostMapping("/enroll")
    public ResponseEntity<?> enrollStudentInSubjects(
            @RequestBody RequestEnrollmentDto dto,
            HttpServletRequest request) {

        try {
        	List<Map<String, Map<String, String>>> result = subjectService.enrollStudentsInSubjects(dto.getEmail(), dto.getSubjectCodes(), request);
	        
	        return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (CustomException e) {
			Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", e.getMessage());
	        
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
    }
 	
 	@GetMapping("/{subjectCode}")
 	public ResponseEntity<?> getSubjectByCode(@PathVariable String subjectCode){
 		
 		try {
			SubjectDto dto = subjectService.getBySubjectCode(subjectCode);
			
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (CustomException e) {
			
			Map<String, String> errorResponse = new HashMap<>();
			
			 errorResponse.put("error", e.getMessage());
		        
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
 	}
 
 	@GetMapping("/user/{subjectCode}")
	public ResponseEntity<?> getAllUsersEnrolledInSubject(@PathVariable String subjectCode, HttpServletRequest request){
		
		try {
			SubjectEnrollmentResponseDto dtos = userService.getStudentEnrolledInSubjects(subjectCode, request);
			
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (CustomException e) {
			
			Map<String, String> errorResponse = new HashMap<>();
			
			 errorResponse.put("error", e.getMessage());
		        
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}
}