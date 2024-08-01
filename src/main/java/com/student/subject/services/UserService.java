package com.student.subject.services;

import java.util.List;

import com.student.subject.dtos.SubjectEnrollmentResponseDto;
import com.student.subject.dtos.UserDto;
import com.student.subject.dtos.UserEnrollmentResponseDto;
import com.student.subject.entities.UserEntity;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	UserDto createUser(UserDto dto);
	
	UserDto getUserByEmail(String email);
	
	List<UserDto> getAllStudentUsers(HttpServletRequest request);
	
	UserDto toDto(UserEntity entity);

	SubjectEnrollmentResponseDto getStudentEnrolledInSubjects(String subjectCode, HttpServletRequest request);

	UserEnrollmentResponseDto getAllSubjectsEnrolledByStudent(String email);
}