package com.student.subject.serviceImpls;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.student.subject.dtos.SubjectDto;
import com.student.subject.dtos.SubjectEnrollmentResponseDto;
import com.student.subject.dtos.UserDto;
import com.student.subject.dtos.UserEnrollmentResponseDto;
import com.student.subject.entities.SubjectsEntity;
import com.student.subject.entities.UserEntity;
import com.student.subject.exceptions.CustomException;
import com.student.subject.jwtUtils.JwtTokenHelper;
import com.student.subject.repos.SubjectRepo;
import com.student.subject.repos.UserRepo;
import com.student.subject.services.SubjectService;
import com.student.subject.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepo userRepo;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	SubjectRepo subjectRepo;
	
	@Autowired
	SubjectService subjectService;
	
	
	
	@Override
	public UserDto createUser(UserDto dto) {
		
		int email = userRepo.countByEmail(dto.getEmailId());
		
		if(email > 0) {
			throw new CustomException("Account Already Exists With Email : " + dto.getEmailId());
		}
		
		int number = userRepo.countByContactNumber(dto.getContactNumber());
		
		if(number > 0) {
			throw new CustomException("Account Already Exists With Contact Number : " + dto.getContactNumber());
		}
		
		UserEntity entity = toEntity(dto);
		
		UserEntity savedEntity = userRepo.save(entity);
		
		return toDto(savedEntity);
	}

	@Override
	public UserDto getUserByEmail(String email) {
		
		UserEntity entity = userRepo.findByEmail(email)
				.orElseThrow(() -> new CustomException("User Not Found With This email"));
		
		return toDto(entity);
	}

	@Override
	public List<UserDto> getAllStudentUsers(HttpServletRequest request) {
		
		String role = jwtTokenHelper.getRoleFromToken(request);
		
		if(role.equals("ADMIN")) {
			List<UserEntity> studentUsers = userRepo.getAllStudentUsers();
			
			List<UserDto> dtos = studentUsers.stream().map(this::toDto).collect(Collectors.toList());
			
			return dtos;
		}
		else {
			return Collections.emptyList();
		}
		
	}
	
	private UserEntity toEntity(UserDto dto) {
		
		UserEntity entity = new UserEntity();
		
		entity.setName(dto.getName());
		entity.setAddress(dto.getAddress());
		entity.setEmailId(dto.getEmailId());
		entity.setPassword(encoder.encode(dto.getPassword()));
		entity.setContactNumber(dto.getContactNumber());
		entity.setUserRole(dto.getUserRole());
		
		return entity;
	}
	
	@Override
	public UserDto toDto(UserEntity entity) {
		
		UserDto dto = new UserDto();
		
		dto.setName(entity.getName());
		dto.setAddress(entity.getAddress());
		dto.setEmailId(entity.getEmailId());
		dto.setPassword(encoder.encode(entity.getPassword()));
		dto.setContactNumber(entity.getContactNumber());
		dto.setUserRole(entity.getUserRole());
		
		return dto;
	}
	
	@Override
	public UserEnrollmentResponseDto getAllSubjectsEnrolledByStudent(String email) {
		
		UserEntity entity = userRepo.findByEmail(email)
				.orElseThrow(() -> new CustomException("Student Not Found For Email : " + email));
		
		Set<String> enrolledSubjectCodes = entity.getEnrollSubjectCodes();
		
		List<SubjectsEntity> enrolledSubjects = subjectRepo.findBySubjectIn(enrolledSubjectCodes);
		
		UserDto userDto = toDto(entity);
		
		List<SubjectDto> subjectDto = enrolledSubjects.stream().map(subjectService::subjectToDto).collect(Collectors.toList());
		
		return new UserEnrollmentResponseDto(userDto, subjectDto);
	}
	
	@Override
	public SubjectEnrollmentResponseDto getStudentEnrolledInSubjects(String subjectCode, HttpServletRequest request) {
		
		String role = jwtTokenHelper.getRoleFromToken(request);
		
		if(role.equals("ADMIN")) {
			
			SubjectsEntity subject = subjectRepo.findBySubjectCode(subjectCode)
		            .orElseThrow(() -> new CustomException("Subject Not Found With Subject Code: " + subjectCode));

		    Set<String> enrolledStudentEmails = subject.getEnrolledStudentsEmail();

		    List<UserEntity> enrolledUsers = userRepo.findByEmailIn(enrolledStudentEmails);
		    
		    List<UserDto> enrolledUsersDto = enrolledUsers.stream().map(this::toDto).collect(Collectors.toList());
		    
		    SubjectDto subjectDto = subjectService.subjectToDto(subject);
		    
		    return new SubjectEnrollmentResponseDto(subjectDto, enrolledUsersDto);
		}
		else {
			throw new CustomException("Students Are Not Allowed to Perform This Action");
		}
	}

}

