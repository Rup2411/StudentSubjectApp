package com.student.subject.dtos;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto {

	@NotBlank(message = "Subject Name is Required")
	private String subjectName;
	
	@Null(message = "Subject Code is Required")
	private String subjectCode;
	
	@JsonIgnore
	private Set<String> enrolledStudentEmails;
}

