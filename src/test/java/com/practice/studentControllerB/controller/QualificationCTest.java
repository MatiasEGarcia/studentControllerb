package com.practice.studentControllerB.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

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
import com.practice.studentControllerB.model.Qualification;
import com.practice.studentControllerB.model.Student;
import com.practice.studentControllerB.utils.MessagesProp;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
class QualificationCTest {

	private static MockHttpServletRequest request;

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MessagesProp messagesProp;

	@Value("${sql.script.create.qualification}")
	private String sqlAddQualification;

	@Value("${sql.script.truncate.qualifications}")
	private String sqlTruncateQualification;

	@Value("${sql.script.create.teacher}")
	private String sqlAddTeacher;

	@Value("${sql.script.truncate.teachers}")
	private String sqlTruncateTeachers;

	@Value("${sql.script.create.course}")
	private String sqlAddCourse;

	@Value("${sql.script.truncate.courses}")
	private String sqlTruncateCourses;

	@Value("${sql.script.create.student}")
	private String sqlAddStudent;

	@Value("${sql.script.truncate.students}")
	private String sqlTruncateStudents;

	@Value("${sql.script.ref.integrity.false}")
	private String sqlRefIntegrityFalse;

	@Value("${sql.script.ref.integrity.true}")
	private String sqlRefIntegrityTrue;

	public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

	Qualification qualification;
	Course course;
	Student student;

	@BeforeAll
	static void mockSetup() {
		request = new MockHttpServletRequest();
	}

	@BeforeEach
	void setUpDatabase() {
		jdbc.update(sqlAddStudent);
		jdbc.update(sqlAddTeacher);
		jdbc.update(sqlAddCourse);
		jdbc.update(sqlAddQualification);
	}

	@BeforeEach
	void setUpVar() {
		course = new Course();
		student = new Student();
		qualification = new Qualification();
	}

	@Test
	void getAllHttpStatusOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/qualificationC/getAll")).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	void getAllHttpStatusNoContent() throws Exception {
		jdbc.update("DELETE FROM qualifications");
		mockMvc.perform(MockMvcRequestBuilders.get("/qualificationC/getAll"))
				.andExpect(status().isNoContent())
				.andExpect(header().string("Info-Header", messagesProp.getMessage("there-no-qualifications")));
	}

	@Test
	void createHttpStatusOk() throws Exception {
		byte quali = 10;
		qualification.setQualification(quali);
		course.setId(1L);
		student.setId(1L);
		qualification.setCourse(course);
		qualification.setStudent(student);
		mockMvc.perform(MockMvcRequestBuilders.post("/qualificationC/create")
				.content(objectMapper.writeValueAsString(qualification)).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	void createFieldsNullQualiWrongRangeHttpStatusBadRequest() throws Exception {
		byte quali = 100;
		qualification.setQualification(quali);
		mockMvc.perform(MockMvcRequestBuilders.post("/qualificationC/create")
				.content(objectMapper.writeValueAsString(qualification)).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.qualification", is(messagesProp.getMessage("vali.qualification-quali-min-max"))))
				.andExpect(jsonPath("$.course", is(messagesProp.getMessage("vali.qualification-course-not-null"))))
				.andExpect(jsonPath("$.student", is(messagesProp.getMessage("vali.qualification-student-not-null"))));
	}

	@Test
	void createStudentAndCourseNoExistHttpStatusBadRequest() throws Exception {
		byte quali = 10;
		qualification.setQualification(quali);
		course.setId(100L);
		student.setId(100L);
		qualification.setCourse(course);
		qualification.setStudent(student);
		mockMvc.perform(MockMvcRequestBuilders.post("/qualificationC/create")
				.content(objectMapper.writeValueAsString(qualification)).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is(messagesProp.getMessage("qualification-course-student-no-found"))));
	}

	@Test
	void getByIdHttpStatusOk() throws Exception {
		course.setId(1L);
		student.setId(1L);
		mockMvc.perform(MockMvcRequestBuilders.get("/qualificationC/getById/{id}", 1)).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.qualification", is(90))).andExpect(jsonPath("$.course.id", is(1)))
				.andExpect(jsonPath("$.student.id", is(1)));

	}

	@Test
	void getByIdNoExistHttpBadRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/qualificationC/getById/{id}", 100))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is(messagesProp.getMessage("qualification-id-not-found"))));
	}

	@Test
	void updateHttpStatusOk() throws Exception {
		qualification.setId(1L);
		byte quali = 8;
		qualification.setQualification(quali);
		course.setId(1L);
		student.setId(1L);
		qualification.setCourse(course);
		qualification.setStudent(student);
		mockMvc.perform(MockMvcRequestBuilders.put("/qualificationC/update")
				.content(objectMapper.writeValueAsString(qualification)).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	void updateQualificationIdNoExistHttpBadRequest() throws Exception {
		qualification.setId(100L);
		byte quali = 8;
		qualification.setQualification(quali);
		course.setId(1L);
		student.setId(1L);
		qualification.setCourse(course);
		qualification.setStudent(student);
		mockMvc.perform(MockMvcRequestBuilders.put("/qualificationC/update")
				.content(objectMapper.writeValueAsString(qualification)).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is(messagesProp.getMessage("qualification-id-not-found"))));

	}

	@Test
	void updateCourseAndStudentNoExistHttpStatusBadRequest() throws Exception {
		qualification.setId(1L);
		byte quali = 8;
		qualification.setQualification(quali);
		course.setId(100L);
		student.setId(100L);
		qualification.setCourse(course);
		qualification.setStudent(student);
		mockMvc.perform(MockMvcRequestBuilders.put("/qualificationC/update")
				.content(objectMapper.writeValueAsString(qualification)).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is(messagesProp.getMessage("qualification-course-student-no-found"))));
	}

	@Test
	void deleteHttpStatusOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/qualificationC/delete/{id}",1))
			.andExpect(status().isOk());
	}

	@Test
	void deleteQualificationIdNoExistHttpStatusBadRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/qualificationC/delete/{id}",100))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message", is(messagesProp.getMessage("qualification-id-not-found"))));
	}
	
	@Test
	void getByCourseIdHttpStatusOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/qualificationC/getByCourseId/{id}",1))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	void getByCourseIdHttpStatusNoContent() throws Exception {
		jdbc.update("DELETE FROM qualifications");
		mockMvc.perform(MockMvcRequestBuilders.get("/qualificationC/getByCourseId/{id}",1))
		.andExpect(status().isNoContent())
		.andExpect(header().string("Info-Header", messagesProp.getMessage("there-no-qualifications")));
	}
	
	@Test
	void getByCourseIdNoExistHttpStatusBadRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/qualificationC/getByCourseId/{id}",100))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message", is(messagesProp.getMessage("course-id-not-found"))));
	}
	
	@Test
	void getByStudentIdHttpStatusOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/qualificationC/getByStudentId/{id}",1))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	void getByStudentIdHttpStatusNoContent() throws Exception {
		jdbc.update("DELETE FROM qualifications");
		mockMvc.perform(MockMvcRequestBuilders.get("/qualificationC/getByStudentId/{id}",1))
		.andExpect(status().isNoContent())
		.andExpect(header().string("Info-Header", messagesProp.getMessage("there-no-qualifications")));
	}
	
	@Test
	void getByStudentIdNoExistHttpStatusBadRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/qualificationC/getByStudentId/{id}",100))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message", is(messagesProp.getMessage("student-id-not-found"))));
	}

	@AfterEach
	void setUpAfterTransaction() {
		jdbc.execute(sqlRefIntegrityFalse);
		jdbc.execute(sqlTruncateStudents);
		jdbc.execute(sqlTruncateTeachers);
		jdbc.execute(sqlTruncateCourses);
		jdbc.execute(sqlTruncateQualification);
		jdbc.execute(sqlRefIntegrityTrue);

	}

}
