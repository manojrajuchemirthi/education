package com.education.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.education.entity.CourseJpa;
import com.education.service.CourseService;

@RestController
@RequestMapping("/api/Courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Endpoint to add a new Course
    @PostMapping("/add")
    public ResponseEntity<CourseJpa> addCourse(@RequestBody CourseJpa CourseJpa) {
        CourseJpa addedCourseJpa = courseService.addCourse(CourseJpa);
        return new ResponseEntity<>(addedCourseJpa, HttpStatus.CREATED);
    }

    // Endpoint to get all Courses
    @GetMapping("/all")
    public ResponseEntity<List<CourseJpa>> getCourses() {
        List<CourseJpa> CourseJpas = courseService.getCourses();
        return new ResponseEntity<>(CourseJpas, HttpStatus.OK);
    }

    // Endpoint to update a Course by ID
    @PutMapping("/update/{CourseJpaId}")
    public ResponseEntity<CourseJpa> updateCourse(@PathVariable Long CourseId, @RequestBody CourseJpa updatedCourse) {
        CourseJpa CourseJpa = courseService.updateCourse(CourseId, updatedCourse);
        return new ResponseEntity<>(CourseJpa, HttpStatus.OK);
    }

    // Endpoint to delete a Course by ID
    @DeleteMapping("/delete/{CourseJpaId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long CourseId) {
        courseService.deleteCourse(CourseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

