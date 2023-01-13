package com.practice.studentControllerB.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

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
	
	@BeforeEach
	void setUpDatabase() {
		jdbc.update(sqlAddStudent);
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
	
	
	@AfterEach
	void setUpAfterTransaction() {
		jdbc.execute(sqlRefIntegrityFalse);
		jdbc.execute(sqlTruncateStudents);
		jdbc.execute(sqlRefIntegrityTrue);
	}
	
}
