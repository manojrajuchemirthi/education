package com.education.serviceImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.education.entity.CourseJpa;
import com.education.entity.StudentJpa;
import com.education.repository.CourseRepository;
import com.education.repository.StudentRepository;
import com.education.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	    @Autowired
	    private StudentRepository studentRepository;
	    
	    @Autowired
	    private CourseRepository courseRepository;
	    
	    @Autowired
	    private RestTemplate restTemplate; // Inject RestTemplate bean

	    private static final String COURSES_API_URL = "http://your-courses-service/api/courses/all"; // Replace with your Courses API URL


	    @Override
	    public StudentJpa registerStudent(StudentJpa student) {
	        // Additional validation or business logic can be added here
	        return studentRepository.save(student);
	    }

	    @Override
	    public List<StudentJpa> getAllStudents() {
	        return studentRepository.findAll();
	    }

	    @Override
	    public StudentJpa getStudentById(Long studentId) {
	        return studentRepository.findById(studentId)
	                .orElseThrow(() -> new RuntimeException("StudentJpa not found with id: " + studentId));
	    }

	    @Override
	    public void allocateStudentToCourse(Long studentId, Long courseId) {
	    	StudentJpa student = studentRepository.findById(studentId)
		            .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

		    CourseJpa course = courseRepository.findById(courseId)
		            .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

		    // Perform the allocation logic by adding the course to the student's courses
		    student.getCourses().add(course);

		    // Save the updated student entity
		    studentRepository.save(student);
	    }

	    @Override
	    public List<StudentJpa> getStudentsWithSelectedCourses(Long courseId) {
	    	return studentRepository.findStudentsByCourseId(courseId);
	    }

	    @Override
	    public StudentJpa updateStudent(Long studentId, StudentJpa updatedStudent) {
	        Optional<StudentJpa> optionalStudent = studentRepository.findById(studentId);

	        if (optionalStudent.isPresent()) {
	            StudentJpa existingStudent = optionalStudent.get();
	            // Update relevant fields with updatedStudent

	            // Additional validation or business logic can be added here

	            return studentRepository.save(existingStudent);
	        } else {
	            throw new RuntimeException("StudentJpa not found with id: " + studentId);
	        }
	    }

	    @Override
	    public void deleteStudent(Long studentId) {
	        studentRepository.deleteById(studentId);
	    }
	    
	    @Override
	    public List<CourseJpa> getAllCourses() {
	    	 CourseJpa[] courses = restTemplate.getForObject(COURSES_API_URL, CourseJpa[].class);
	         return Arrays.asList(courses);
	    }
	    
	    @Override
	    public void updateCoursesForStudent(Long studentId, List<Long> courseIds) {
	        StudentJpa student = studentRepository.findById(studentId)
	                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

	        List<CourseJpa> courses = courseRepository.findAllById(courseIds);

	        // Perform the update logic here, for example, setting the courses for the student
	        student.setCourses(new HashSet<>(courses));

	        // Save the updated student entity
	        studentRepository.save(student);
	    }
	
}
