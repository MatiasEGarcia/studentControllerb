package com.practice.studentControllerB.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.studentControllerB.model.Course;
import com.practice.studentControllerB.model.Teacher;
import com.practice.studentControllerB.utils.MessagesProp;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
class CourseCTest {

	private static MockHttpServletRequest request;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MessagesProp messagesProp;
	
	@Value("${sql.script.create.teacher}")
	private String sqlAddTeacher;

	@Value("${sql.script.truncate.teachers}")
	private String sqlTruncateTeachers;
	
	@Value("${sql.script.create.course}")
	private String sqlAddCourse;
	
	@Value("${sql.script.truncate.courses}")
	private String sqlTruncateCourses;
	
	@Value("${sql.script.ref.integrity.false}")
	private String sqlRefIntegrityFalse;

	@Value("${sql.script.ref.integrity.true}")
	private String sqlRefIntegrityTrue;
	
	public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

	Teacher teacher;
	Course course;
	
	@BeforeAll
	static void mockSetup() {
		request = new MockHttpServletRequest();
	}
	
	@BeforeEach
	void setUpDatabase() {
		jdbc.update(sqlAddTeacher);
		jdbc.update(sqlAddCourse);
	}
	
	@BeforeEach
	void teacherCourseSetUp() {
		teacher = new Teacher();
		course = new Course();
	}
	
	@Test
	void getAllHttpStatusOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/courseC/getAll"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$",hasSize(1)));
	}
	
	@Test
	void getAllHttpStatusNoContent() throws Exception{
		jdbc.update("DELETE FROM courses");
		mockMvc.perform(MockMvcRequestBuilders.get("/courseC/getAll"))
		.andExpect(status().isNoContent())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.message",is(messagesProp.getMessage("there-no-courses"))));
	}
	
	
	@AfterEach
	void setUpAfterTransaction() {
		jdbc.execute(sqlRefIntegrityFalse);
		jdbc.execute(sqlTruncateTeachers);
		jdbc.execute(sqlTruncateCourses);
		jdbc.execute(sqlRefIntegrityTrue);
	}
}
