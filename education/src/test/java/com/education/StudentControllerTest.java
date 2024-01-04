package com.education;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;


import com.education.controller.StudentController;
import com.education.entity.CourseJpa;
import com.education.entity.StudentJpa;
import com.education.service.StudentService;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testRegisterStudent() throws Exception {
        // Mocking the service behavior
        when(studentService.registerStudent(any(StudentJpa.class)))
                .thenReturn(new StudentJpa(/* mock student data */));

        // Performing the request and verifying the response
        String requestBody = "{"
                + "\"fullName\": \"John Doe\","
                + "\"email\": \"john.doe@example.com\","
                + "\"telephoneNumber\": \"+1234567890\","
                + "\"address\": \"123 Main St\""
                + "}";

        // Performing the request and verifying the response
        mockMvc.perform(post("/api/students/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.telephoneNumber").value("+1234567890"))
                .andExpect(jsonPath("$.address").value("123 Main St"));
}

    @Test
    public void testGetAllStudents() throws Exception {
        // Mocking the service behavior
        when(studentService.getAllStudents())
                .thenReturn(Arrays.asList(new StudentJpa(/* mock student data */)));

        // Performing the request and verifying the response
        mockMvc.perform(get("/api/students/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName", is("John Doe")));
    }

    @Test
    public void testGetStudentById() throws Exception {
        // Mocking the service behavior
        when(studentService.getStudentById(anyLong()))
                .thenReturn(new StudentJpa(/* mock student data */));

        // Performing the request and verifying the response
        mockMvc.perform(get("/api/students/{studentId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName", is("John Doe")));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        // Mocking the service behavior
        when(studentService.updateStudent(anyLong(), any(StudentJpa.class)))
                .thenReturn(new StudentJpa(/* mock student data */));

        // Mock request body as JSON
        String requestBody = "{"
                + "\"fullName\": \"Updated Name\","
                + "\"email\": \"updated.email@example.com\","
                + "\"telephoneNumber\": \"+9876543210\","
                + "\"address\": \"456 Updated St\""
                + "}";

        // Performing the request and verifying the response
        mockMvc.perform(put("/api/students/update/{studentId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Updated Name"));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        // Performing the request and verifying the response
        mockMvc.perform(delete("/api/students/delete/{studentId}", 1L))
                .andExpect(status().isNoContent());
        
        // Verify that the service method is called with the correct argument
        verify(studentService, times(1)).deleteStudent(1L);
    }

    @Test
    public void testGetAllCourses() throws Exception {
        // Mocking the service behavior
        when(studentService.getAllCourses())
                .thenReturn(Arrays.asList(new CourseJpa(/* mock course data */)));

        // Performing the request and verifying the response
        mockMvc.perform(get("/api/students/all-courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName", is("John Doe")));
    }

    @Test
    public void testGetStudentsWithSelectedCourses() throws Exception {
        // Mocking the service behavior
        when(studentService.getStudentsWithSelectedCourses(anyLong()))
                .thenReturn(Arrays.asList(new StudentJpa(/* mock student data */)));

        // Performing the request and verifying the response
        mockMvc.perform(get("/api/students/with-course/{courseId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName", is("John Doe")));
    }

    @Test
    public void testUpdateCoursesForStudent() throws Exception {
        // Mocking the service behavior
        doNothing().when(studentService).updateCoursesForStudent(eq(1L), anyList());

        // Mock request body as JSON (assuming it's an array of course IDs)
        String requestBody = "[1, 2, 3]";

        // Performing the request and verifying the response
        mockMvc.perform(put("/api/students/{studentId}/update-courses", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }
}
