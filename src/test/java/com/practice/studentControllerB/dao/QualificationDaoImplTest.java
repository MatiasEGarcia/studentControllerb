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

import com.practice.studentControllerB.model.Course;
import com.practice.studentControllerB.model.Qualification;
import com.practice.studentControllerB.model.Student;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class QualificationDaoImplTest {

	@Autowired
	private QualificationDao dao;
	
	@Autowired
	private JdbcTemplate jdbc;

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
	
	Qualification qualification;
	Course course;
	Student student;
	
	@BeforeEach
	void setUpData(){
		jdbc.update(sqlAddTeacher);
		jdbc.update(sqlAddCourse);
		jdbc.update(sqlAddStudent);
		jdbc.update(sqlAddQualification);
		qualification = new Qualification();
		course = new Course();
		student = new Student();
	}
	
	@Test
	void getAllReturnNull() {
		jdbc.update("DELETE FROM qualifications");
		assertNull(dao.getAll());
	}
	
	@Test
	void getAllReturnNotNull() {
		assertNotNull(dao.getAll());
	}
	
	@Test
	void createReturn1() {
		byte q = 80;
		qualification.setQualification(q);
		course.setId(1L);
		student.setId(1L);
		qualification.setCourse(course);
		qualification.setStudent(student);
		assertEquals(1, dao.create(qualification));
	}
	
	@Test
	void getByIdNoExistReturnNull() {
		assertNull(dao.getById(100L));
	}
	
	@Test
	void getByIdExistReturnNotNull() {
		assertNotNull(dao.getById(1L));
	}
	
	@Test
	void updateIdNoExistReturn0(){
		byte q = 80;
		qualification.setQualification(q);
		course.setId(100L);
		student.setId(1L);
		qualification.setCourse(course);
		qualification.setStudent(student);
		assertEquals(0, dao.update(qualification));
	}
	
	@Test
	void updateIdExistReturn1(){
		byte q = 80;
		qualification.setQualification(q);
		course.setId(1L);
		student.setId(1L);
		qualification.setCourse(course);
		qualification.setStudent(student);
		assertEquals(0, dao.update(qualification));
	}
	
	@Test
	void deleteIdExistReturn1() {
		assertEquals(1, dao.delete(1L));
	}
	
	@Test
	void deleteIdNoExistReturn0() {
		assertEquals(0, dao.delete(100L));
	}
	
	@Test
	void getByCourseIdNoExistReturnNull() {
		assertNull(dao.getByCourseId(100L));
	}
	
	@Test
	void getByCourseIdExistReturnNotNull() {
		assertNotNull(dao.getByCourseId(1L));
	}
	
	@Test 
	void getByStudentIdNoExistReturnNull(){
		assertNull(dao.getByCourseId(100L));
	}
	
	@Test 
	void getByStudentIdExistReturnNotNull(){
		assertNotNull(dao.getByCourseId(1L));
	}
	
	@AfterEach
	void setUpAfterTransaction() {
		jdbc.execute(sqlRefIntegrityFalse);
		jdbc.execute(sqlTruncateTeachers);
		jdbc.execute(sqlTruncateStudents);
		jdbc.execute(sqlTruncateCourses);
		jdbc.execute(sqlTruncateQualification);
		jdbc.execute(sqlRefIntegrityTrue);
		
	}
	
	
	
	
	
}
