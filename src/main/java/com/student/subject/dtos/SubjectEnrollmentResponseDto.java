package com.student.subject.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectEnrollmentResponseDto  {

	private SubjectDto subject;
	
	private List<UserDto> students;
}