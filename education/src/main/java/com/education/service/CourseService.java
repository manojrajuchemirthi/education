package com.education.service;

import java.util.List;

import com.education.entity.CourseJpa;

public interface CourseService {
	CourseJpa addCourse(CourseJpa course);
    List<CourseJpa> getCourses();
    CourseJpa updateCourse(Long courseId, CourseJpa updatedCourse);
    void deleteCourse(Long courseId);
}

