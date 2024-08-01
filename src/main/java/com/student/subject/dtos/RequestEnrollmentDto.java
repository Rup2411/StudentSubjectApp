package com.student.subject.dtos;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestEnrollmentDto {

	private String email;
	
	private Set<String> subjectCodes;
}
