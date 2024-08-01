package com.student.subject.serviceImpls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.subject.dtos.SubjectDto;
import com.student.subject.entities.SubjectsEntity;
import com.student.subject.entities.UserEntity;
import com.student.subject.exceptions.CustomException;
import com.student.subject.jwtUtils.JwtTokenHelper;
import com.student.subject.repos.SubjectRepo;
import com.student.subject.repos.UserRepo;
import com.student.subject.services.SubjectService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	@Autowired
	SubjectRepo subjectRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	JwtTokenHelper jwtTokenHelper;

	@Override
	public SubjectDto createSubject(SubjectDto dto, HttpServletRequest request) {
		
		String role = jwtTokenHelper.getRoleFromToken(request);
		
		if(role.equals("ADMIN")) {
			
			SubjectsEntity entity = subjectRepo.findBySubjectCode(dto.getSubjectCode())
					.orElseThrow(() -> new CustomException("Subject Not Found With Subject Code : " + dto.getSubjectCode()));
			
			int subject = subjectRepo.countBySubjectCode(dto.getSubjectCode());
			
			if(subject > 0) {
				throw new CustomException("Subject " + entity.getSubjectName() + " Already Been Created With Subject Code " + dto.getSubjectCode());
			}
			SubjectsEntity addSubject = toEntity(dto);
			
			SubjectsEntity savedSubject = subjectRepo.save(addSubject);
			
			return subjectToDto(savedSubject);
		}
		else {
			throw new CustomException("Students Are Not Allowed to Create Subjects");
		}
	}

	@Override
	public SubjectDto getBySubjectCode(String subjectCode) {
		
		SubjectsEntity entity = subjectRepo.findBySubjectCode(subjectCode)
				.orElseThrow(() -> new CustomException("Subject Not Found With Subject Code : " + subjectCode));
		
		return subjectToDto(entity);
	}

	@Override
	public List<SubjectDto> getAllSubjects() {
		
		List<SubjectsEntity> allSubjects = subjectRepo.findAll();
		
		List<SubjectDto> dtos = allSubjects.stream().map(this::subjectToDto).collect(Collectors.toList());
		
		return dtos;
	}
	
	private SubjectsEntity toEntity(SubjectDto dto) {
		
		SubjectsEntity entity = new SubjectsEntity();
		
		entity.setSubjectCode(dto.getSubjectCode());
		entity.setSubjectName(dto.getSubjectName());
		
		return entity;
	}
	
	@Override
	public SubjectDto subjectToDto(SubjectsEntity entity) {
		
		SubjectDto dto = new SubjectDto();
		
		dto.setSubjectCode(entity.getSubjectCode());
		dto.setSubjectName(entity.getSubjectName());
		
		return dto;
	}
	
	
	@Override
	public List<Map<String, Map<String, String>>> enrollStudentsInSubjects(String email, Set<String> subjectCodes, HttpServletRequest request) {

		Map<String, String> enrolledSubjects = new HashMap<>();
	    Map<String, String> notEnrolledSubjects = new HashMap<>();
	    
	    String role = jwtTokenHelper.getRoleFromToken(request);
	    String requestEmail = jwtTokenHelper.getEmailFromToken(request);
	    
	    if (role.equals("STUDENT") && !requestEmail.equals(email)) {
            throw new CustomException("Students can only enroll themselves.");
        }

	    UserEntity entity = userRepo.findByEmail(email)
	            .orElseThrow(() -> new CustomException("Student Not Found For Email: " + email));

	    for (String subjectCode : subjectCodes) {
	        try {
	            SubjectsEntity subject = subjectRepo.findBySubjectCode(subjectCode)
	                    .orElseThrow(() -> new CustomException("Subject Not Found With Subject Code: " + subjectCode));

	            if (!entity.getEnrollSubjectCodes().contains(subjectCode)) {
	                
	            	entity.getEnrollSubjectCodes().add(subjectCode);
	                
	                enrolledSubjects.put(subjectCode, "Enrolled");
	            } else {
	            	
	                notEnrolledSubjects.put(subjectCode, "Already Enrolled");
	            }

	            if (!subject.getEnrolledStudentsEmail().contains(email)) {
	            	
	                subject.getEnrolledStudentsEmail().add(email);
	            }

	            userRepo.save(entity);
	            
	            subjectRepo.save(subject);

	        } catch (CustomException e) {
	            notEnrolledSubjects.put(subjectCode, e.getMessage());
	        }
	    }
	    
	    List<Map<String, Map<String, String>>> result = new ArrayList<>();
	    Map<String, Map<String, String>> enrolledMap = new HashMap<>();
	    enrolledMap.put("enrolled", enrolledSubjects);

	    Map<String, Map<String, String>> nonEnrolledMap = new HashMap<>();
	    nonEnrolledMap.put("notEnrolled", notEnrolledSubjects);

	    result.add(enrolledMap);
	    result.add(nonEnrolledMap);

	    return result;
	}
	
}
