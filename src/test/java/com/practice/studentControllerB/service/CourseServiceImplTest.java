package com.practice.studentControllerB.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.practice.studentControllerB.dao.CourseDao;
import com.practice.studentControllerB.dao.TeacherDao;
import com.practice.studentControllerB.model.Course;
import com.practice.studentControllerB.model.Shift;
import com.practice.studentControllerB.model.Teacher;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

	@Mock private CourseDao courseDao;
	@Mock private TeacherDao teacherDao;
	@InjectMocks private CourseServiceImpl courseService;
	Course course;
	Teacher teacher;
	
	@BeforeEach
	void studentSetUp() {
		course = new Course();
		teacher = new Teacher();
	}
	
	@Test
	void getAllReturnNull() {
		when(courseDao.getAll()).thenReturn(null);
		assertNull(courseService.getAll());
		verify(courseDao).getAll();
	}
	@Test
	void getAllReturnNotNullEquals() {
		List<Course> courses = new ArrayList<>();
		courses.add(course);
		when(courseDao.getAll()).thenReturn(courses);
		assertEquals(courses,courseService.getAll());
		verify(courseDao).getAll();
	}
	
	@Test
	void createArgumentNullThrow() {
		assertThrows(IllegalArgumentException.class, () -> {courseService.create(null);});
		verify(courseDao,never()).getByTitle(null);
		verify(courseDao,never()).create(null);
	}
	
	@Test
	void createWithTitleAlreadyUsedThrow() {
		course.setTitle("Chinese");
		when(courseDao.getByTitle(course.getTitle())).thenReturn(course);
		assertThrows(IllegalArgumentException.class, () -> {courseService.create(course);});
		verify(courseDao).getByTitle(course.getTitle());
		verify(courseDao,never()).create(null);
	}
	
	@Test
	void createTitleNotNullAndTitleAvailable() {
		course.setTitle("Chinese");
		when(courseDao.getByTitle(course.getTitle())).thenReturn(null);
		when(courseDao.create(course)).thenReturn(1);
		assertEquals(1, courseService.create(course));
		verify(courseDao).getByTitle(course.getTitle());
		verify(courseDao).create(course);
	}
	
	@Test
	void getByIdNotNull() {
		course.setId(1L);
		when(courseDao.getById(course.getId())).thenReturn(course);
		assertEquals(course, courseService.getById(course.getId()));
		verify(courseDao).getById(course.getId());
	}
	
	@Test
	void getByIdNull() {
		course.setId(1L);
		when(courseDao.getById(course.getId())).thenReturn(null);
		assertNull(courseService.getById(course.getId()));
		verify(courseDao).getById(course.getId());
	}
	
	@Test
	void updateIdNoExistThrow() {
		course.setId(1L);
		when(courseDao.getById(course.getId())).thenReturn(null);
		assertThrows(IllegalArgumentException.class, () -> {courseService.update(course);});
		verify(courseDao).getById(course.getId());
		verify(courseDao,never()).update(course);
	}
	
	@Test
	void updateIdExistReturn1() {
		course.setId(1L);
		when(courseDao.getById(course.getId())).thenReturn(course);
		when(courseDao.update(course)).thenReturn(1);
		assertEquals(1, courseService.update(course));
		verify(courseDao).getById(course.getId());
		verify(courseDao).update(course);
	}
	
	@Test
	void deleteIdNoExistThrow() {
		course.setId(1L);
		when(courseDao.getById(course.getId())).thenReturn(null);
		assertThrows(IllegalArgumentException.class, () -> {courseService.delete(course.getId());});
		verify(courseDao).getById(course.getId());
		verify(courseDao,never()).delete(course.getId());
	}
	
	@Test
	void deleteIdExistReturn1() {
		course.setId(1L);
		when(courseDao.getById(course.getId())).thenReturn(course);
		when(courseDao.delete(course.getId())).thenReturn(1);
		assertEquals(1, courseService.delete(course.getId()));
		verify(courseDao).getById(course.getId());
		verify(courseDao).delete(course.getId());
	}
	
	@Test
	void getByTitleReturnNull() {
		course.setTitle("Chinese");
		when(courseDao.getByTitle(course.getTitle())).thenReturn(null);
		assertNull(courseService.getByTitle(course.getTitle()));
		verify(courseDao).getByTitle(course.getTitle());
	}
	
	@Test
	void getByTitleReturnNotNull() {
		course.setTitle("Chinese");
		when(courseDao.getByTitle(course.getTitle())).thenReturn(course);
		assertEquals(course,courseService.getByTitle(course.getTitle()));
		verify(courseDao).getByTitle(course.getTitle());
	}
	
	@Test
	void getByShiftNoExistThrow() {
		assertThrows(IllegalArgumentException.class, () -> {courseService.getByShift("mor");});
	}
	
	@Test
	void getByShiftExistButLowerCaseEqualsReturnNotNull() {
		List<Course> courses = new ArrayList<>();
		course.setShift(Shift.MORNING.toString());
		courses.add(course);
		when(courseDao.getByShift(course.getShift().toUpperCase())).thenReturn(courses);
		assertEquals(courses, courseService.getByShift(course.getShift().toLowerCase()));
		verify(courseDao).getByShift(course.getShift().toUpperCase());
	}
	
	@Test
	void getByShiftExistButUpperCaseEqualsReturnNull() {
		List<Course> courses = new ArrayList<>();
		course.setShift(Shift.MORNING.toString());
		courses.add(course);
		when(courseDao.getByShift(course.getShift())).thenReturn(null);
		assertNull(courseService.getByShift(course.getShift()));
		verify(courseDao).getByShift(course.getShift());
	}
	
	@Test
	void getByTeacherIdNoExist() {
		teacher.setId(1L);
		when(teacherDao.getById(teacher.getId())).thenReturn(null);
		assertThrows(IllegalArgumentException.class,() -> {courseService.getByTeacherId(teacher.getId());});
		verify(teacherDao).getById(teacher.getId());
		verify(courseDao,never()).getByTeacherId(teacher.getId());
	}
	
	@Test
	void getByTeacherIdExistReturnNull() {
		teacher.setId(1L);
		when(teacherDao.getById(teacher.getId())).thenReturn(teacher);
		when(courseDao.getByTeacherId(teacher.getId())).thenReturn(null);
		assertNull(courseService.getByTeacherId(teacher.getId()));
		verify(teacherDao).getById(teacher.getId());
		verify(courseDao).getByTeacherId(teacher.getId());
	}
	
	@Test
	void getByTeacherIdExistReturnNotNull() {
		teacher.setId(1L);
		when(teacherDao.getById(teacher.getId())).thenReturn(teacher);
		when(courseDao.getByTeacherId(teacher.getId())).thenReturn(course);
		assertEquals(course,courseService.getByTeacherId(teacher.getId()));
		verify(teacherDao).getById(teacher.getId());
		verify(courseDao).getByTeacherId(teacher.getId());
	}
}
