package com.education.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.entity.CourseJpa;
import com.education.repository.CourseRepository;
import com.education.service.CourseService;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public CourseJpa addCourse(CourseJpa course) {
        return courseRepository.save(course);
    }

    @Override
    public List<CourseJpa> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public CourseJpa updateCourse(Long courseId, CourseJpa updatedCourseJpa) {
        Optional<CourseJpa> optionalCourseJpa = courseRepository.findById(courseId);

        if (optionalCourseJpa.isPresent()) {
            CourseJpa existingCourseJpa = optionalCourseJpa.get();
            existingCourseJpa.setName(updatedCourseJpa.getName());


            return courseRepository.save(existingCourseJpa);
        } else {
            throw new RuntimeException("Course not found with id: " + courseId);
        }
    }

    @Override
    public void deleteCourse(Long courseId) {
        Optional<CourseJpa> optionalCourseJpa = courseRepository.findById(courseId);

        if (optionalCourseJpa.isPresent()) {
            // Additional validation or business logic can be added here
            courseRepository.deleteById(courseId);
        } else {
            // Handle course not found
            throw new RuntimeException("Course not found with id: " + courseId);
        }
    }
}

