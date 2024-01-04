package com.education.service;

import java.util.List;

import com.education.entity.CourseJpa;
import com.education.entity.StudentJpa;

public interface StudentService {

    StudentJpa registerStudent(StudentJpa student);

    List<StudentJpa> getAllStudents();

    StudentJpa getStudentById(Long studentId);

    void allocateStudentToCourse(Long studentId, Long courseId);

    List<StudentJpa> getStudentsWithSelectedCourses(Long courseId);

    StudentJpa updateStudent(Long studentId, StudentJpa updatedStudent);

    void deleteStudent(Long studentId);
    
    List<CourseJpa> getAllCourses();
    
    void updateCoursesForStudent(Long studentId, List<Long> courseIds);
    
}

