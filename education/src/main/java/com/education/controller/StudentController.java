package com.education.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.education.entity.CourseJpa;
import com.education.entity.StudentJpa;
import com.education.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<StudentJpa> registerStudent(@RequestBody StudentJpa student) {
        StudentJpa registeredStudent = studentService.registerStudent(student);
        return new ResponseEntity<>(registeredStudent, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentJpa>> getAllStudents() {
        List<StudentJpa> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentJpa> getStudentById(@PathVariable Long studentId) {
        StudentJpa student = studentService.getStudentById(studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<StudentJpa> updateStudent(@PathVariable Long studentId, @RequestBody StudentJpa updatedStudent) {
        StudentJpa student = studentService.updateStudent(studentId, updatedStudent);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/all-courses")
    public ResponseEntity<List<CourseJpa>> getAllCourses() {
        List<CourseJpa> courses = studentService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
    
    @GetMapping("/with-course/{courseId}")
    public ResponseEntity<List<StudentJpa>> getStudentsWithSelectedCourses(@PathVariable Long courseId) {
        List<StudentJpa> students = studentService.getStudentsWithSelectedCourses(courseId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    
    @PutMapping("/{studentId}/update-courses")
    public ResponseEntity<Void> updateCoursesForStudent(@PathVariable Long studentId, @RequestBody List<Long> courseIds) {
        studentService.updateCoursesForStudent(studentId, courseIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
