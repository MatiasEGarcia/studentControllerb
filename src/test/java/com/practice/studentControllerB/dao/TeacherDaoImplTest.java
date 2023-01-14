package com.practice.studentControllerB.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import com.practice.studentControllerB.model.QualificationE;
import com.practice.studentControllerB.model.Teacher;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
class TeacherDaoImplTest {

	@Autowired
	private TeacherDao dao;
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Value("${sql.script.truncate.teachers}")
	private String sqlTruncateTeachers;
	
	@Value("${sql.script.create.teacher}")
	private String sqlAddTeacher;

	@Value("${sql.script.ref.integrity.false}")
	private String sqlRefIntegrityFalse;

	@Value("${sql.script.ref.integrity.true}")
	private String sqlRefIntegrityTrue;
	
	Teacher teacher;

	@BeforeEach
	void setUpTeacher() {
		jdbc.update(sqlAddTeacher);
		teacher = new Teacher();
		byte age = 40;
		teacher.setId(1);
		teacher.setName("Tanaka");
		teacher.setLastname("Fujimori");
		teacher.setEmail("tanaF@gmail.com");
		teacher.setAge(age);
		teacher.setQualification(QualificationE.UNIVERSITARY.toString());
		teacher.setNationality("Japanese");
		
	}
	
	@Test
	void getAllReturnNotNull() {
		assertNotNull(dao.getAll());
	}
	
	@Test
	void getAllReturnNull() {
		jdbc.update("DELETE FROM teachers");
		assertNull(dao.getAll());
	}
	
	@Test
	void createReturn1() {
		assertEquals(1,dao.create(teacher));
	}
	
	@Test
	void getByIdNoExistReturnNull() {
		assertNull(dao.getById(100L));
	}
	
	@Test
	void getByIdExistReturnNotNull() {
		assertNotNull(dao.getById(teacher.getId()));
	}
	
	@Test
	void updateReturn1() {
		assertEquals(1, dao.update(teacher));
	}
	
	@Test
	void updateWihtIdNoExistReturn0(){
		teacher.setId(100L);
		assertEquals(0, dao.update(teacher));
	}
	
	@Test
	void deleteReturn1() {
		assertEquals(1, dao.delete(teacher.getId()));
	}
	
	@Test
	void deleteWihtIdNoExistReturn0(){
		assertEquals(0, dao.delete(100L));
	}
	
	@Test
	void getByQualificationReturnNull() {
		assertNull(dao.getByQualification(QualificationE.DOCTORATE.toString()));//in the database there is not a teacher with this qualification
	}
	
	@Test
	void getByQualificationReturnNotNull() {
		assertNotNull(dao.getByQualification(teacher.getQualification()));
	}
	
	@Test
	void getByNationalityReturnNull() {
		assertNull(dao.getByNationality("Japanese"));
	}
	@Test
	void getByNationalityReturnNotNull() {
		assertNotNull(dao.getByNationality("Chinese"));
	}
	
	@AfterEach
	void setUpAfterTransaction() {
		jdbc.execute(sqlRefIntegrityFalse);
		jdbc.execute(sqlTruncateTeachers);
		jdbc.execute(sqlRefIntegrityTrue);
	}
}
