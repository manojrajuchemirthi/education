package com.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.education.entity.CourseJpa;

@Repository
public interface CourseRepository extends JpaRepository<CourseJpa, Long> {
}
