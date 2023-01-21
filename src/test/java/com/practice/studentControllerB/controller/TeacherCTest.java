package com.practice.studentControllerB.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
import com.practice.studentControllerB.dao.TeacherDao;
import com.practice.studentControllerB.model.QualificationE;
import com.practice.studentControllerB.model.Teacher;
import com.practice.studentControllerB.utils.MessagesProp;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
class TeacherCTest {

	private static MockHttpServletRequest request;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private TeacherDao teacherD;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private MessagesProp messagesProp;
	
	@Value("${sql.script.truncate.teachers}")
	private String sqlTruncateTeachers;
	
	@Value("${sql.script.create.teacher}")
	private String sqlAddTeacher;
	
	@Value("${sql.script.ref.integrity.false}")
	private String sqlRefIntegrityFalse;

	@Value("${sql.script.ref.integrity.true}")
	private String sqlRefIntegrityTrue;
	
	public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;
	
	Teacher teacher;
	
	@BeforeAll
	static void mockSetup() {
		request = new MockHttpServletRequest();
	}
	
	@BeforeEach
	void setUp() {
		jdbc.update(sqlAddTeacher);
		teacher = new Teacher();
	}
	
	@Test
	void getAllReturnOkHttpStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/teacherC/getAll"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$",hasSize(1)));
	}
	
	@Test
	void getAllReturnNoContentHttpStatus() throws Exception {
		jdbc.update("DELETE FROM teachers");
		mockMvc.perform(MockMvcRequestBuilders.get("/teacherC/getAll"))
		.andExpect(status().isNoContent())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.message",is(messagesProp.getMessage("there-no-teachers"))));
	}
	
	@Test
	void getByidNoExistHttpStatusBadRequest() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/teacherC/getById/{id}",100))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.message",is(messagesProp.getMessage("teacher-id-not-found"))));
	}
	
	@Test
	void getByIdHttpStatusOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/teacherC/getById/{id}",1))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("Xiao")))
		.andExpect(jsonPath("$.lastname", is("Ming")))
		.andExpect(jsonPath("$.email", is("xiaoM@gmail.com")))
		.andExpect(jsonPath("$.age", is(40)))
		.andExpect(jsonPath("$.qualification", is("UNIVERSITARY")))
		.andExpect(jsonPath("$.nationality", is("Chinese")));
	}
	
	@Test
	void createTeacherEmailAlreadyUsedBadRequest() throws Exception{
		teacher.setName("xiao");
		teacher.setLastname("mi");
		teacher.setEmail("xiaoM@gmail.com");
		byte age = 30;
		teacher.setAge(age);
		teacher.setNationality("Chinese");
		teacher.setQualification(QualificationE.TERTIARY.toString());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/teacherC/create")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(teacher)))
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is(messagesProp.getMessage("teacher-email-already-used"))));
	}
	
	@Test
	void createWithFieldsBlankHttpStatusBadRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/teacherC/create")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(teacher)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.name",is(messagesProp.getMessage("vali.person.name-not-blank"))))
				.andExpect(jsonPath("$.lastname",is(messagesProp.getMessage("vali.person.lastname-not-blank"))))
				.andExpect(jsonPath("$.email",is(messagesProp.getMessage("vali.person.email-not-blank"))))
				.andExpect(jsonPath("$.age",is(messagesProp.getMessage("vali.person.age-max-min"))))
				.andExpect(jsonPath("$.qualification",is(messagesProp.getMessage("vali.teacher.qualification-not-blank"))))
				.andExpect(jsonPath("$.nationality",is(messagesProp.getMessage("vali.teacher.nationality-not-blank"))));
	}
	
	@Test
	void createHttpStatusOk() throws Exception {
		teacher.setName("xiao");
		teacher.setLastname("mi");
		teacher.setEmail("xiaoMi@gmail.com");
		byte age = 30;
		teacher.setAge(age);
		teacher.setNationality("Chinese");
		teacher.setQualification(QualificationE.TERTIARY.toString());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/teacherC/create")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(teacher)))
				.andExpect(status().isOk());
	}
	
	@Test
	void updateIdNoExistHttStatusBadRequest() throws Exception {
		teacher.setName("xiao");
		teacher.setLastname("mi");
		teacher.setEmail("xiaoMi@gmail.com");
		byte age = 30;
		teacher.setAge(age);
		teacher.setNationality("Chinese");
		teacher.setQualification(QualificationE.TERTIARY.toString());
		
		mockMvc.perform(MockMvcRequestBuilders.put("/teacherC/update")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(teacher)))
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message",is(messagesProp.getMessage("teacher-id-not-found"))));
	}
	@Test
	void updateHttpStatusOk() throws Exception {
		teacher.setId(1L);
		teacher.setName("xiao");
		teacher.setLastname("mi");
		teacher.setEmail("xiaoMi@gmail.com");
		byte age = 30;
		teacher.setAge(age);
		teacher.setNationality("Chinese");
		teacher.setQualification(QualificationE.TERTIARY.toString());
		
		mockMvc.perform(MockMvcRequestBuilders.put("/teacherC/update")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(teacher)))
				.andExpect(status().isOk());
	}
	
	@Test
	void deleteHttpStatusOk() throws Exception {
		teacher.setId(1L);
		mockMvc.perform(MockMvcRequestBuilders.delete("/teacherC/delete/{id}",teacher.getId()))
		.andExpect(status().isOk());
		
		assertNull(teacherD.getById(teacher.getId()));
	}
	
	@Test
	void deleteNoExistIdHttpStatusBadRequest() throws Exception {
		teacher.setId(100L);
		mockMvc.perform(MockMvcRequestBuilders.delete("/teacherC/delete/{id}",teacher.getId()))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message", is(messagesProp.getMessage("teacher-id-not-found"))));
	}
	
	@Test
	void getByQualificationHttpStatusOk() throws Exception {
		String qualific = QualificationE.UNIVERSITARY.toString();
		mockMvc.perform(MockMvcRequestBuilders.get("/teacherC/getByQualification/{qualific}",qualific))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$",hasSize(1)));
	}
	
	@Test
	void getByQualificationHttpStatusNoContent() throws Exception {
		String qualific = QualificationE.DOCTORATE.toString();
		mockMvc.perform(MockMvcRequestBuilders.get("/teacherC/getByQualification/{qualific}",qualific))
		.andExpect(status().isNoContent())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.message",is(messagesProp.getMessage("there-no-teachers"))));
	}
	
	@Test
	void getByQualificationHttpStatusBadRequest() throws Exception {
		String qualific = "noExistQualification";
		mockMvc.perform(MockMvcRequestBuilders.get("/teacherC/getByQualification/{qualific}",qualific))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.message",is(messagesProp.getMessage("qualification-not-found"))));
	}
	
	@Test
	void getByNationalityHttpStatusOk() throws Exception {
		String nationality = "Chinese";
		mockMvc.perform(MockMvcRequestBuilders.get("/teacherC/getByNationality/{nationality}",nationality))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$",hasSize(1)));
	}
	
	@Test
	void getByNationalityHttpStatusNoContent() throws Exception {
		String nationality = "Japanese";
		mockMvc.perform(MockMvcRequestBuilders.get("/teacherC/getByNationality/{nationality}",nationality))
		.andExpect(status().isNoContent())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.message",is(messagesProp.getMessage("there-no-teachers"))));
	}
	
	
	@AfterEach
	void setUpAfterTransaction() {
		jdbc.execute(sqlRefIntegrityFalse);
		jdbc.execute(sqlTruncateTeachers);
		jdbc.execute(sqlRefIntegrityTrue);
	}
}
