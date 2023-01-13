package com.practice.studentControllerB.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.practice.studentControllerB.dao.StudentDao;
import com.practice.studentControllerB.model.Student;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

	@Mock private StudentDao studentDao;
	@InjectMocks private StudentServiceImpl studentService;
	Student student = new Student();
	
	@BeforeEach
	void studentSetUp() {
		Calendar calendar = Calendar.getInstance();
		byte age = 20;
		student.setId(1L);
		student.setName("Matias");
		student.setLastname("Garcia");
		student.setEmail("mati@gmail.com");
		student.setAge(age);
		student.setFavoriteLanguage("Chinese");
		student.setAddmissionDate(calendar);
	}
	
	@Test
	void getAllReturnEmptyList() {
		when(studentDao.getAll()).thenReturn(null);
		assertTrue(studentService.getAll().isEmpty());
		verify(studentDao).getAll();
	}
	
	@Test
	void getAllReturnNoEmptyList() {
		List<Student> students = new ArrayList<>();
		students.add(student);
		when(studentDao.getAll()).thenReturn(students);
		assertFalse(studentService.getAll().isEmpty());
		verify(studentDao).getAll();
	}
	
	@Test
	void createArgumentNull() {
		assertThrows(IllegalArgumentException.class,() -> {studentService.create(null);},"If argument is null should throw IllegalArgumentException");
		verify(studentDao,never()).create(null);
	}
	
	@Test
	void createEmailAlreadyExist() {
		when(studentDao.getByEmail(student.getEmail())).thenReturn(student);
		assertThrows(IllegalArgumentException.class,() -> {studentService.create(student);},"Student email is already used, so throw IllegalArgumentException");
		verify(studentDao).getByEmail(student.getEmail());
		verify(studentDao,never()).create(student);
	}
	
	@Test
	void createSuccefuly() {
		when(studentDao.getByEmail(student.getEmail())).thenReturn(null);
		when(studentDao.create(student)).thenReturn(1);
		
		assertEquals(1, studentService.create(student));
		
		verify(studentDao).getByEmail(student.getEmail());
		verify(studentDao).create(student);
	}
}




