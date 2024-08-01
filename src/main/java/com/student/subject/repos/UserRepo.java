package com.student.subject.repos;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.student.subject.entities.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

	@Query(value = "select * from user_entity where email = :x", nativeQuery = true)
	Optional<UserEntity> findByEmail(@Param("x") String username);

	@Query(value = "select COUNT(*) from user_entity where email = :x", nativeQuery = true)
	int countByEmail(@Param("x") String emailId);

	@Query(value = "select COUNT(*) from user_entity where contact_number = :x", nativeQuery = true)
	int countByContactNumber(@Param("x") String contactNumber);

	
	@Query(value = "select * from user_entity where user_role = 'STUDENT'", nativeQuery = true)
	List<UserEntity> getAllStudentUsers();

	@Query(value = "SELECT u.* FROM user_entity u WHERE u.email IN :x", nativeQuery = true)
	List<UserEntity> findByEmailIn(@Param("x") Set<String> enrolledStudentEmails);

}
