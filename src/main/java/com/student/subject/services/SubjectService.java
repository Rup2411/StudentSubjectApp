package com.student.subject.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.student.subject.dtos.SubjectDto;
import com.student.subject.entities.SubjectsEntity;

import jakarta.servlet.http.HttpServletRequest;

public interface SubjectService {

	SubjectDto getBySubjectCode(String subjectCode);
	
	List<SubjectDto> getAllSubjects();

	SubjectDto createSubject(SubjectDto dto, HttpServletRequest request);

	List<Map<String, Map<String, String>>> enrollStudentsInSubjects(String email, Set<String> subjectCodes,
			HttpServletRequest request);

	SubjectDto subjectToDto(SubjectsEntity entity);
}
