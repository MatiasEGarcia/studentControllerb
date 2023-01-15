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
import com.practice.studentControllerB.model.Shift;
import com.practice.studentControllerB.model.Teacher;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class CourseDaoImplTest {

	@Autowired
	private CourseDao dao;
	
	@Autowired
	private JdbcTemplate jdbc;
	
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
	
	Course course;
	Teacher teacher;
	
	@BeforeEach
	void setUpData() {
		jdbc.update(sqlAddTeacher);
		jdbc.update(sqlAddCourse);
		teacher = new Teacher();
		course = new Course();
	}
	
	@Test
	void getAllNotNull() {
		assertNotNull(dao.getAll());
	}
	
	@Test
	void getAllNull() {
		jdbc.update("DELETE FROM courses");
		assertNull(dao.getAll());
	}

	@Test
	void createReturn1() {
		teacher.setId(1);
		course.setTitle("Japanese");
		course.setTeacher(teacher);
		course.setShift(Shift.NIGHT.toString());
		assertEquals(1, dao.create(course));
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
	void updateReturn1() {
		teacher.setId(1);
		course.setId(1L);
		course.setTitle("Chinese");
		course.setTeacher(teacher);
		course.setShift(Shift.NIGHT.toString());
		assertEquals(1, dao.update(course));
	}
	
	@Test
	void updateCourseIdNoExistReturn0(){
		teacher.setId(1);
		course.setId(100L);
		course.setTitle("Chinese");
		course.setTeacher(teacher);
		course.setShift(Shift.NIGHT.toString());
		assertEquals(0, dao.update(course));
	}
	
	@Test
	void deleteReturn1() {
		assertEquals(1, dao.delete(1L));
	}
	
	@Test
	void deleteIdNoExistReturn0() {
		assertEquals(0, dao.delete(100L));
	}
	
	@Test
	void getByTitleNoExistReturnNull() {
		assertNull(dao.getByTitle("Japanese"));
	}
	
	@Test
	void getByTitleExistReturnNotNull() {
		assertNotNull(dao.getByTitle("Chinese"));
	}
	
	@Test
	void getByShiftReturnNull() {
		assertNull(dao.getByShift(Shift.NIGHT.toString()));
	}
	
	@Test
	void getByShiftReturnNotNull() {
		assertNotNull(dao.getByShift(Shift.MORNING.toString()));
	}
	
	@Test
	void getByTeacherIdNoExistReturnNull() {
		assertNull(dao.getByTeacherId(100L));
	}
	
	@Test
	void getByTeacherIdExistReturnNotNull() {
		assertNotNull(dao.getByTeacherId(1L));
	}
	
	
	@AfterEach
	void setUpAfterTransaction() {
		jdbc.execute(sqlRefIntegrityFalse);
		jdbc.execute(sqlTruncateTeachers);
		jdbc.execute(sqlTruncateCourses);
		jdbc.execute(sqlRefIntegrityTrue);
	}
}
