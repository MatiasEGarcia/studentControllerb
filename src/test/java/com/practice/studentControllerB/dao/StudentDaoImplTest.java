package com.practice.studentControllerB.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
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
		student.setId(2L);
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
	
	@AfterEach
	void setUpAfterTransaction() {
		jdbc.execute(sqlRefIntegrityFalse);
		jdbc.execute(sqlTruncateStudents);
		jdbc.execute(sqlRefIntegrityTrue);
	}
	
}
