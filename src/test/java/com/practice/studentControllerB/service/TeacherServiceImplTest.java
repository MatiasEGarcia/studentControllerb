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

import com.practice.studentControllerB.dao.TeacherDao;
import com.practice.studentControllerB.model.QualificationE;
import com.practice.studentControllerB.model.Teacher;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

	@Mock private TeacherDao teacherDao;
	@InjectMocks private TeacherServiceImpl teacherService;
	Teacher teacher;
	
	@BeforeEach
	void setUpTeacher() {
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
	void getAllReturnNull() {
		when(teacherDao.getAll()).thenReturn(null);
		assertNull(teacherService.getAll());
		verify(teacherDao).getAll();
	}
	
	@Test
	void getAllReturnNotNull() {
		List<Teacher> teachers = new ArrayList<>();
		teachers.add(teacher);
		when(teacherDao.getAll()).thenReturn(teachers);
		assertEquals(teachers,teacherService.getAll());
		verify(teacherDao).getAll();
	}
	
	@Test
	void createArgumentNull() {
		assertThrows(IllegalArgumentException.class, () -> {teacherService.create(null);});
		verify(teacherDao,never()).create(null);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
