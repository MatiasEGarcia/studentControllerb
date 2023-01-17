package com.practice.studentControllerB.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.studentControllerB.config.prop.ControllerProp;
import com.practice.studentControllerB.dao.StudentDao;
import com.practice.studentControllerB.model.Student;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
class StudentCTest {

	private static MockHttpServletRequest request;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private StudentDao studentD;
	
	@Autowired
	private ControllerProp contProp;
	
	@Value("${sql.script.create.student}")
	private String sqlAddStudent;

	@Value("${sql.script.truncate.students}")
	private String sqlTruncateStudents;

	@Value("${sql.script.ref.integrity.false}")
	private String sqlRefIntegrityFalse;

	@Value("${sql.script.ref.integrity.true}")
	private String sqlRefIntegrityTrue;
	
	public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;
	
	Student student;
	
	@BeforeAll
	static void mockSetup() {
		request = new MockHttpServletRequest();
	}
	
	@BeforeEach
	void setUpDatabase() {
		jdbc.update(sqlAddStudent);
	}
	
	@BeforeEach
	void studentSetUp() {
		student = new Student();
	}
	
	@Test
	void getAllReturnOkHttpStatus() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/studentC/getAll"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$",hasSize(1)));
	}
	
	@Test
	void getAllReturnNoContentHttpStatus() throws Exception{
		jdbc.update("DELETE FROM students"); //now getAll will return null
		mockMvc.perform(MockMvcRequestBuilders.get("/studentC/getAll"))
		.andExpect(status().isNoContent())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.message",is(contProp.getNoStudents())));
	}
	
	@AfterEach
	void setUpAfterTransaction() {
		jdbc.execute(sqlRefIntegrityFalse);
		jdbc.execute(sqlTruncateStudents);
		jdbc.execute(sqlRefIntegrityTrue);
	}
	
	
	
	
	
	
	
	
	
	
	
}
