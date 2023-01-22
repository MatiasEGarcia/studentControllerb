package com.practice.studentControllerB.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
import com.practice.studentControllerB.dao.CourseDao;
import com.practice.studentControllerB.model.Course;
import com.practice.studentControllerB.model.Shift;
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
	private CourseDao courseD;
	
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
	
	@Test
	void createHttpStatusOk() throws Exception {
		course.setTitle("Japanese");
		teacher.setId(1L);
		course.setTeacher(teacher);
		course.setShift(Shift.MORNING.toString());
		mockMvc.perform(MockMvcRequestBuilders.post("/courseC/create")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(course)))
				.andExpect(status().isOk());
	}
	
	@Test
	void createCourseFieldsNullHttpStatusBadRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/courseC/create")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(course)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.title", is(messagesProp.getMessage("vali.course-title-not-blank"))))
				.andExpect(jsonPath("$.shift", is(messagesProp.getMessage("vali.course-shift-not-blank"))))
				.andExpect(jsonPath("$.teacher", is(messagesProp.getMessage("vali.course-teacher-not-null"))));
	}
	
	@Test
	void createCourseTitleAlreadyUsedHttpStatusBadRequest() throws Exception {
		course.setTitle("Chinese");
		teacher.setId(1L);
		course.setTeacher(teacher);
		course.setShift(Shift.MORNING.toString());
		mockMvc.perform(MockMvcRequestBuilders.post("/courseC/create")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(course)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is(messagesProp.getMessage("course-title-already-used"))));
	}
	
	@Test
	void updateHttpStatusOk() throws Exception{
		course.setId(1L);
		course.setTitle("Japanese");
		teacher.setId(1L);
		course.setTeacher(teacher);
		course.setShift(Shift.MORNING.toString());
		mockMvc.perform(MockMvcRequestBuilders.put("/courseC/update")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(course)))
				.andExpect(status().isOk());
	}
	
	@Test
	void updateIdNoExistHttpStatusOk() throws Exception{
		course.setId(100L); //there is not exist course with this id
		course.setTitle("Japanese");
		teacher.setId(1L);
		course.setTeacher(teacher);
		course.setShift(Shift.MORNING.toString());
		mockMvc.perform(MockMvcRequestBuilders.put("/courseC/update")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(course)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is(messagesProp.getMessage("course-id-not-found"))));
	}
	
	@Test
	void updateCourseTitleAlreadyUsedHttpStatusBadRequest() throws Exception{
		course.setId(1L);
		course.setTitle("Chinese"); //this title is alreadyUsed
		teacher.setId(1L);
		course.setTeacher(teacher);
		course.setShift(Shift.MORNING.toString());
		mockMvc.perform(MockMvcRequestBuilders.put("/courseC/update")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(course)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is(messagesProp.getMessage("course-title-already-used"))));
	}
	
	@Test
	void updateCourseTeacherIdNoExistHttpStatusBadRequest() throws Exception{
		course.setId(1L);
		course.setTitle("Japanese");
		teacher.setId(100L); //there is not exist teacher with this id
		course.setTeacher(teacher);
		course.setShift(Shift.MORNING.toString());
		mockMvc.perform(MockMvcRequestBuilders.put("/courseC/update")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(course)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is(messagesProp.getMessage("teacher-id-not-found"))));
	}
	
	@Test
	void deleteHttpStatusOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/courseC/delete/{id}",1))
			.andExpect(status().isOk());
	}
	
	@Test
	void deleteIdNoExistHttpStatusBadRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/courseC/delete/{id}",100))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message", is(messagesProp.getMessage("course-id-not-found"))));
	}
	
	@Test
	void getByTitleHttpStatusOk()throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/courseC/getByTitle/{title}","Chinese"))
		.andExpect(status().isOk());
	}
	
	@Test
	void getByTitleNoExistHttpStatusNoContent()throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/courseC/getByTitle/{title}","Japanese"))
		.andExpect(status().isNoContent())
		.andExpect(jsonPath("$.message", is(messagesProp.getMessage("course-title-not-found"))));
	}
	
	@Test
	void getByShiftHttpStatusOk() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/courseC/getByShift/{shift}","morning"))
		.andExpect(status().isOk());
	}
	
	@Test
	void getByShiftNoExistHttpStatusBadRequest() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/courseC/getByShift/{shift}","morn"))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message", is(messagesProp.getMessage("shift-not-found"))));
	}
	
	@Test
	void getByShiftHttpStatusNoContent() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/courseC/getByShift/{shift}","night"))
		.andExpect(status().isNoContent())
		.andExpect(jsonPath("$.message", is(messagesProp.getMessage("there-no-courses"))));
	}
	
	
	
	@Test
	void getByTeacherIdHttpStatusOk() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/courseC/getByTeacherId/{id}",1))
		.andExpect(status().isOk());
	}
	
	@Test
	void getByTeacherIdNoExistHttpStatusBadRequest() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/courseC/getByTeacherId/{id}",100))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message", is(messagesProp.getMessage("teacher-id-not-found"))));
	}
	
	@Test
	void getByTeacherIdHttpStatusNoContent() throws Exception{
		courseD.delete(1L);
		mockMvc.perform(MockMvcRequestBuilders.get("/courseC/getByTeacherId/{id}",1))
		.andExpect(status().isNoContent())
		.andExpect(jsonPath("$.message", is(messagesProp.getMessage("there-no-courses"))));
	}
	
	@AfterEach
	void setUpAfterTransaction() {
		jdbc.execute(sqlRefIntegrityFalse);
		jdbc.execute(sqlTruncateTeachers);
		jdbc.execute(sqlTruncateCourses);
		jdbc.execute(sqlRefIntegrityTrue);
	}
}
