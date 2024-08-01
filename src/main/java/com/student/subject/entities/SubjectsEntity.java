package com.student.subject.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subject_entity")
public class SubjectsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "subject_name")
	private String subjectName;
	
	@Column(name = "subject_code")
	private String subjectCode;
	
	@ElementCollection
	@Column(name = "enrolled_students")
	Set<String> enrolledStudentsEmail = new HashSet<>();
}
