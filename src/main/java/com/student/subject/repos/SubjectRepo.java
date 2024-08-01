package com.student.subject.repos;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.student.subject.entities.SubjectsEntity;

public interface SubjectRepo extends JpaRepository<SubjectsEntity, Long> {

	@Query(value = "select * from subject_entity where subject_code = :x", nativeQuery = true)
	Optional<SubjectsEntity> findBySubjectCode(@Param("x") String subjectCode);

	@Query(value = "select u.* FROM subject_entity u WHERE u.subject_code IN :x", nativeQuery = true)
	List<SubjectsEntity> findBySubjectIn(@Param("x") Set<String> enrolledSubjectCodes);

	@Query(value = "select COUNT(*) from subject_entity where subject_code = :x", nativeQuery = true)
	int countBySubjectCode(@Param("x") String subjectCode);

}