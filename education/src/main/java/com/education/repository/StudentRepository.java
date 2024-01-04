package com.education.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.education.entity.StudentJpa;

@Repository
public interface StudentRepository extends JpaRepository<StudentJpa, Long> {
	
	@Query("SELECT DISTINCT s FROM StudentJpa s JOIN FETCH s.courses c WHERE c.id = :courseId")
	List<StudentJpa> findStudentsByCourseId(@Param("courseId") Long courseId);
}
