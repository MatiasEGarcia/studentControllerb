package com.practice.studentControllerB.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import com.practice.studentControllerB.model.Student;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class StudentDaoImplTest {

	@Autowired
	private StudentDao dao;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Value("${sql.script.create.student}")
	private String sqlAddStudent;

	@Value("${sql.script.truncate.students}")
	private String sqlTruncateStudents;

	@Value("${sql.script.ref.integrity.false}")
	private String sqlRefIntegrityFalse;

	@Value("${sql.script.ref.integrity.true}")
	private String sqlRefIntegrityTrue;
	
	Student student;
	
	@BeforeEach
	void setUpData() {
		jdbc.update(sqlAddStudent);
		student = new Student();
		Calendar calendar = Calendar.getInstance();
		byte age = 20;
		student.setId(1L);
		student.setName("Franco");
		student.setLastname("Castaña");
		student.setEmail("francoCast@gmail.com");
		student.setAge(age);
		student.setFavoriteLanguage("English");
		student.setAddmissionDate(calendar);
	}

	@Test
	void getAllReturnNull() {
		jdbc.update("DELETE FROM students");
		assertNull(dao.getAll());
	}
	
	@Test
	void getAllReturnNotNull() {
		assertNotNull(dao.getAll());
	}
	
	@Test
	void createReturn1() { //Successfully created
		assertEquals(1, dao.create(student));
	}
	
	@Test
	void getByIdNoExistReturnNull() {
		assertNull(dao.getById(2L));
	}
	
	@Test
	void getByIdExistReturnStudent() {
		assertNotNull(dao.getById(1L));
	}
	
	@Test
	void updateReturn1() {
		assertEquals(1,dao.update(student));
	}
	
	@Test
	void updateWihtIdNoExistReturn0() {
		student.setId(100L);
		assertEquals(0,dao.update(student));
	}
	
	@Test
	void deleteReturn1() {
		assertEquals(1, dao.delete(student.getId()));
	}
	
	@Test
	void deleteWithIdNoExistReturn0(){
		student.setId(100L);
		assertEquals(0 , dao.delete(student.getId()));
	}
	
	@Test
	void getByEmailExistReturnNotNull() {
		assertNotNull(dao.getByEmail("mati@gmail.com")); //this email is in the application_test
	}
	
	@Test
	void getByEmailNoExistReturnNull() {
		assertNull(dao.getByEmail("m@gmail.com"));
	}
	
	@Test
	void getByFavoriteLanguageNotNull() {
		assertNotNull(dao.favoriteLanguage("Chinese")); //this is in the application_test
	}
	
	@Test
	void getByFavoriteLanguageNull() {
		assertNull(dao.favoriteLanguage("German"));
	}
	
	@AfterEach
	void setUpAfterTransaction() {
		jdbc.execute(sqlRefIntegrityFalse);
		jdbc.execute(sqlTruncateStudents);
		jdbc.execute(sqlRefIntegrityTrue);
	}
	
}
