package com.practice.studentControllerB.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;

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
import com.practice.studentControllerB.dao.StudentDao;
import com.practice.studentControllerB.model.Student;
import com.practice.studentControllerB.utils.MessagesProp;

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
	private MessagesProp messagesProp;
	
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
		.andExpect(jsonPath("$.message",is(messagesProp.getMessage("contr.there-no-students"))));
		
	}
	
	@Test
	void getByIdReturnBadRequestHttpStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/studentC/getById/{id}",100))//student with id don't exist
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.message",is(messagesProp.getMessage("e.student-id-not-found"))));
	}
	
	@Test
	void getByIdReturnOkHttpStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/studentC/getById/{id}",1))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.id",is(1)))
		.andExpect(jsonPath("$.name",is("Matias")))
		.andExpect(jsonPath("$.lastname",is("Garcia")))
		.andExpect(jsonPath("$.email",is("mati@gmail.com")))
		.andExpect(jsonPath("$.age",is(23)))
		.andExpect(jsonPath("$.addmissionDate",is("2010-10-20")))
		.andExpect(jsonPath("$.favoriteLanguage",is("Chinese")));
	}
	
	@Test
	void createStudentWithEmailAlreadyUsed() throws Exception {
		assertNotNull(studentD.getByEmail("mati@gmail.com"));
		student.setName("Matias");
		student.setLastname("Andreo");
		student.setEmail("mati@gmail.com");
		byte age = 25;
		student.setAge(age);
		student.setFavoriteLanguage("English");
		Calendar calendar = Calendar.getInstance();
		student.setAddmissionDate(calendar);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/studentC/create")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(student)))
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message",is(messagesProp.getMessage("e.student-email-already-used"))));
	}
	
	@Test
	void createCreatedHttpStatus() throws Exception {
		student.setName("Matias");
		student.setLastname("Andreo");
		student.setEmail("matiAndreo@gmail.com");
		byte age = 25;
		student.setAge(age);
		student.setFavoriteLanguage("English");
		Calendar calendar = Calendar.getInstance();
		student.setAddmissionDate(calendar);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/studentC/create")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(student)))
				.andExpect(status().isCreated());
		
		assertNotNull(studentD.getByEmail("matiAndreo@gmail.com"));
	}
	
	@Test
	void createWithFieldsNullBadRequest() throws Exception {
		//email and lastname are null
		student.setName("Matias");
		byte age = 25;
		student.setAge(age);
		student.setFavoriteLanguage("English");
		Calendar calendar = Calendar.getInstance();
		student.setAddmissionDate(calendar);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/studentC/create")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(student)))
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email",is(messagesProp.getMessage("vali.person.email-not-blank"))))
				.andExpect(jsonPath("$.lastname",is(messagesProp.getMessage("vali.person.lastname-not-blank"))));
	}
	
	@Test
	void updateIdNoExistBadRequestHttpStatus() throws Exception {
		student.setId(0L);
		student.setName("Matias");
		student.setLastname("Andreo");
		student.setEmail("matiAndreo@gmail.com");
		byte age = 25;
		student.setAge(age);
		student.setFavoriteLanguage("English");
		Calendar calendar = Calendar.getInstance();
		student.setAddmissionDate(calendar);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/studentC/update")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(student)))
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message",is(messagesProp.getMessage("e.student-id-not-found"))));
	}
	
	@Test
	void updateOkHttpStatus() throws Exception {
		student.setId(1L);
		student.setName("Matias");
		student.setLastname("Andreo");
		student.setEmail("matiAndreo@gmail.com");
		byte age = 25;
		student.setAge(age);
		student.setFavoriteLanguage("English");
		Calendar calendar = Calendar.getInstance();
		student.setAddmissionDate(calendar);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/studentC/update")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(student)))
				.andExpect(status().isOk());
	}
	
	@Test
	void deleteExistIdHttpStatusOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/studentC/delete/{id}",1))
				.andExpect(status().isOk());
		
		assertNull(studentD.getById(1L));
	}
	
	@Test
	void deleteNoExistIdHttpStatusBadRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/studentC/delete/{id}",100))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message",is(messagesProp.getMessage("e.student-id-not-found"))));
	}
	
	@Test
	void getByEmailExistHttpStatusOk() throws Exception {
		String email = "mati@gmail.com";
		assertNotNull(studentD.getByEmail(email));
		mockMvc.perform(MockMvcRequestBuilders.get("/studentC/getByEmail/{email}",email))
		.andExpect(status().isOk());
	}
	
	@Test
	void getByEmailNoExistHttpStatusBadRequest() throws Exception {
		String email = "marco@gmail.com";
		assertNull(studentD.getByEmail(email));
		mockMvc.perform(MockMvcRequestBuilders.get("/studentC/getByEmail/{email}",email))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message",is(messagesProp.getMessage("contr.student-email-not-found"))));
	}
	
	@Test
	void getByFavoriteLanguageHttpStatusOk() throws Exception {
		String language = "Chinese";
		assertNotNull(studentD.getByfavoriteLanguage(language));
		mockMvc.perform(MockMvcRequestBuilders.get("/studentC/getByFavLanguage/{favLanguage}",language))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$",hasSize(1)));
	}
	
	@Test
	void getByFavoriteLanguageHttpStatusNoContent() throws Exception {
		String language = "Japanese";
		assertNull(studentD.getByfavoriteLanguage(language));
		mockMvc.perform(MockMvcRequestBuilders.get("/studentC/getByFavLanguage/{favLanguage}",language))
			.andExpect(status().isNoContent())
			.andExpect(jsonPath("$.message",is(messagesProp.getMessage("contr.there-no-students"))));
	}
	
	@AfterEach
	void setUpAfterTransaction() {
		jdbc.execute(sqlRefIntegrityFalse);
		jdbc.execute(sqlTruncateStudents);
		jdbc.execute(sqlRefIntegrityTrue);
	}
	
	
	
	
	
	
	
	
	
	
	
}
