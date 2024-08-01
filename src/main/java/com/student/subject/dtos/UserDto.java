package com.student.subject.dtos;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	@NotBlank(message = "Full Name is Required")
	private String name;
	
	@NotBlank(message = "Address is Required")
	private String address;
	
	@Email
	@NotNull(message = "Invalid Email")
	private String emailId;
	
	@NotNull(message = "Password is Required")
	private String password;
	
	@NotNull(message = "Contact Number is Required")
	@Size(min = 10, max = 10, message = "Enter Valid Contact Number")
	private String contactNumber;
	
	@NotNull(message = "Specify User's Role, Either Student or Admin")
	private String userRole;
	
	@JsonIgnore
	private Set<String> enrolledSubjectCodes;
	
}