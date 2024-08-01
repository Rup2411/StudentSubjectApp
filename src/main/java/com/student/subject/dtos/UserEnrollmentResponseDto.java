package com.student.subject.dtos;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEnrollmentResponseDto {

	private UserDto user;
	
	private List<SubjectDto> subjects;
}