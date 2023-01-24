package com.practice.studentControllerB.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.practice.studentControllerB.utils.MessagesProp;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

	@Mock private MessagesProp messagesProp;
	@Mock private CourseDao courseDao;
	@Mock private TeacherDao teacherDao;
	@InjectMocks private CourseServiceImpl courseService;
	List<Course> courses ;
	Course course;
	Teacher teacher;
	
	@BeforeEach
	void studentSetUp() {
		courses = new ArrayList<>();
		course = new Course();
		course.setId(1L);
		course.setTitle("Chinese");
		course.setShift(Shift.MORNING.toString());
		teacher = new Teacher();
		teacher.setId(1L);
		course.setTeacher(teacher);
	}
	
	@Test
	void getAllReturnNull() {
		when(courseDao.getAll()).thenReturn(null);
		assertNull(courseService.getAll());
		verify(courseDao).getAll();
	}
	@Test
	void getAllReturnNotNull() {
		courses.add(course);
		when(courseDao.getAll()).thenReturn(courses);
		assertNotNull(courseService.getAll());
		verify(courseDao).getAll();
	}
	
	@Test
	void createArgumentNullThrow() {
		assertThrows(IllegalArgumentException.class, () -> {courseService.create(null);});
		verify(teacherDao,never()).getById(null);
		verify(courseDao,never()).getByTitle(null);
		verify(courseDao,never()).create(null);
	}
	
	@Test
	void createWithTeacherNullThrow() {
		course.setTeacher(null);
		assertThrows(IllegalArgumentException.class, () -> {courseService.create(course);});
		verify(teacherDao,never()).getById(null);
		verify(courseDao,never()).getByTitle(course.getTitle());
		verify(courseDao,never()).create(course);
	}
	
	@Test
	void createWithTeacherIdNoExistThrow() {
		course.getTeacher().setId(100L);
		assertThrows(IllegalArgumentException.class, () -> {courseService.create(course);});
		verify(teacherDao).getById(course.getTeacher().getId());
		verify(courseDao,never()).getByTitle(course.getTitle());
		verify(courseDao,never()).create(course);
	}
	
	@Test
	void createWithTitleAlreadyUsedThrow() {
		when(courseDao.getByTitle(course.getTitle())).thenReturn(course);
		when(teacherDao.getById(course.getTeacher().getId())).thenReturn(teacher);
		assertThrows(IllegalArgumentException.class, () -> {courseService.create(course);});
		verify(teacherDao).getById(course.getTeacher().getId());
		verify(courseDao).getByTitle(course.getTitle());
		verify(courseDao,never()).create(null);
	}
	
	@Test
	void createTitleNotNullAndTitleAvailable() {
		when(courseDao.getByTitle(course.getTitle())).thenReturn(null);
		when(teacherDao.getById(course.getTeacher().getId())).thenReturn(teacher);
		when(courseDao.create(course)).thenReturn(1);
		assertEquals(1, courseService.create(course));
		verify(teacherDao).getById(course.getTeacher().getId());
		verify(courseDao).getByTitle(course.getTitle());
		verify(courseDao).create(course);
	}
	
	@Test
	void getByIdNotNull() {
		when(courseDao.getById(course.getId())).thenReturn(course);
		assertEquals(course, courseService.getById(course.getId()));
		verify(courseDao).getById(course.getId());
	}
	
	@Test
	void getByIdNull() {
		when(courseDao.getById(course.getId())).thenReturn(null);
		assertNull(courseService.getById(course.getId()));
		verify(courseDao).getById(course.getId());
	}
	
	@Test
	void updateArgumentNull() {
		assertThrows(IllegalArgumentException.class, () -> {courseService.update(null);});
	}
	
	@Test
	void updateIdNoExistThrow() {
		when(teacherDao.getById(course.getTeacher().getId())).thenReturn(teacher);
		when(courseDao.getById(course.getId())).thenReturn(null);
		assertThrows(IllegalArgumentException.class, () -> {courseService.update(course);});
		verify(teacherDao).getById(course.getTeacher().getId());
		verify(courseDao).getById(course.getId());
		verify(courseDao,never()).getByTitle(course.getTitle());
		verify(courseDao,never()).update(course);
	}
	
	@Test
	void updateIdExistReturn1() {
		when(teacherDao.getById(course.getTeacher().getId())).thenReturn(teacher);
		when(courseDao.getByTitle(course.getTitle())).thenReturn(null);
		when(courseDao.getById(course.getId())).thenReturn(course);
		when(courseDao.update(course)).thenReturn(1);
		assertEquals(1, courseService.update(course));
		verify(teacherDao).getById(course.getTeacher().getId());
		verify(courseDao).getById(course.getId());
		verify(courseDao).getByTitle(course.getTitle());
		verify(courseDao).update(course);
	}
	
	@Test
	void deleteIdNoExistThrow() {
		when(courseDao.getById(course.getId())).thenReturn(null);
		assertThrows(IllegalArgumentException.class, () -> {courseService.delete(course.getId());});
		verify(courseDao).getById(course.getId());
		verify(courseDao,never()).delete(course.getId());
	}
	
	@Test
	void deleteIdExistReturn1() {
		when(courseDao.getById(course.getId())).thenReturn(course);
		when(courseDao.delete(course.getId())).thenReturn(1);
		assertEquals(1, courseService.delete(course.getId()));
		verify(courseDao).getById(course.getId());
		verify(courseDao).delete(course.getId());
	}
	
	@Test
	void getByTitleReturnNull() {
		when(courseDao.getByTitle(course.getTitle())).thenReturn(null);
		assertNull(courseService.getByTitle(course.getTitle()));
		verify(courseDao).getByTitle(course.getTitle());
	}
	
	@Test
	void getByTitleReturnNotNull() {
		when(courseDao.getByTitle(course.getTitle())).thenReturn(course);
		assertNotNull(courseService.getByTitle(course.getTitle()));
		verify(courseDao).getByTitle(course.getTitle());
	}
	
	@Test
	void getByShiftNoExistThrow() {
		assertThrows(IllegalArgumentException.class, () -> {courseService.getByShift("mor");});
	}
	
	@Test
	void getByShiftExistButLowerCaseEqualsReturnNotNull() {
		when(courseDao.getByShift(course.getShift().toUpperCase())).thenReturn(courses);
		assertNotNull(courseService.getByShift(course.getShift().toLowerCase()));
		verify(courseDao).getByShift(course.getShift().toUpperCase());
	}
	
	@Test
	void getByShiftExistButUpperCaseEqualsReturnNull() {
		when(courseDao.getByShift(course.getShift())).thenReturn(null);
		assertNull(courseService.getByShift(course.getShift()));
		verify(courseDao).getByShift(course.getShift());
	}
	
	@Test
	void getByTeacherIdNoExist() {
		when(teacherDao.getById(teacher.getId())).thenReturn(null);
		assertThrows(IllegalArgumentException.class,() -> {courseService.getByTeacherId(teacher.getId());});
		verify(teacherDao).getById(teacher.getId());
		verify(courseDao,never()).getByTeacherId(teacher.getId());
	}
	
	@Test
	void getByTeacherIdExistReturnNull() {
		when(teacherDao.getById(teacher.getId())).thenReturn(teacher);
		when(courseDao.getByTeacherId(teacher.getId())).thenReturn(null);
		assertNull(courseService.getByTeacherId(teacher.getId()));
		verify(teacherDao).getById(teacher.getId());
		verify(courseDao).getByTeacherId(teacher.getId());
	}
	
	@Test
	void getByTeacherIdExistReturnNotNull() {
		when(teacherDao.getById(teacher.getId())).thenReturn(teacher);
		when(courseDao.getByTeacherId(teacher.getId())).thenReturn(course);
		assertNotNull(courseService.getByTeacherId(teacher.getId()));
		verify(teacherDao).getById(teacher.getId());
		verify(courseDao).getByTeacherId(teacher.getId());
	}
}
