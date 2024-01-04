package com.education;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.education.controller.CourseController;
import com.education.controller.StudentController;
import com.education.entity.CourseJpa;
import com.education.entity.StudentJpa;
import com.education.service.CourseService;
import com.education.service.StudentService;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    public void testAddCourse() throws Exception {
        // Mocking the service behavior
        when(courseService.addCourse(any(CourseJpa.class)))
                .thenReturn(new CourseJpa(1L, "Math 101"));

        // Mock request body as JSON
        String requestBody = "{"
                + "\"courseName\": \"Math 101\""
                + "}";

        // Performing the request and verifying the response
        mockMvc.perform(post("/api/Courses/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.courseName").value("Math 101"));
    }

    @Test
    public void testGetCourses() throws Exception {
        // Mocking the service behavior
        when(courseService.getCourses())
                .thenReturn(Arrays.asList(new CourseJpa(1L, "Math 101")));

        // Performing the request and verifying the response
        mockMvc.perform(get("/api/Courses/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].courseName").value("Math 101"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateCourse() throws Exception {
        // Mocking the service behavior
        when(courseService.updateCourse(anyLong(), any(CourseJpa.class)))
                .thenReturn(new CourseJpa(1L, "Updated Math 101"));

        // Mock request body as JSON
        String requestBody = "{"
                + "\"courseName\": \"Updated Math 101\""
                + "}";

        // Performing the request and verifying the response
        mockMvc.perform(put("/api/Courses/update/{CourseId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.courseName").value("Updated Math 101"));
    }

    @Test
    public void testDeleteCourse() throws Exception {
        // Performing the request and verifying the response
        mockMvc.perform(delete("/api/Courses/delete/{CourseId}", 1L))
                .andExpect(status().isNoContent());

        // Verify that the service method is called with the correct argument
        verify(courseService, times(1)).deleteCourse(1L);
    }
}
