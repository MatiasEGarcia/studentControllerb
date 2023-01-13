package com.practice.studentControllerB.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
	void getAllReturnNull() {
		when(studentDao.getAll()).thenReturn(null);
		assertNull(studentService.getAll());
		verify(studentDao).getAll();
	}
	
	@Test
	void getAllReturnNotNull() {
		List<Student> students = new ArrayList<>();
		students.add(student);
		when(studentDao.getAll()).thenReturn(students);
		assertNotNull(studentService.getAll());
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
	void createSuccefully() {
		when(studentDao.getByEmail(student.getEmail())).thenReturn(null);
		when(studentDao.create(student)).thenReturn(1);
		
		assertEquals(1, studentService.create(student));
		
		verify(studentDao).getByEmail(student.getEmail());
		verify(studentDao).create(student);
	}
	
	@Test
	void getByIdNoExistReturnNull() {
		when(studentDao.getById(student.getId())).thenReturn(null);
		assertNull(studentDao.getById(student.getId()));
		verify(studentDao).getById(student.getId());
	}
	
	@Test
	void getByIdExistReturnStudent() {
		when(studentDao.getById(student.getId())).thenReturn(student);
		assertEquals(student, studentService.getById(student.getId()));
		verify(studentDao).getById(student.getId());
	}
	
	@Test
	void updateArgumentNull() {
		assertThrows(IllegalArgumentException.class,() -> {studentService.update(null);},"If argument is null should throw IllegalArgumentException");
		verify(studentDao,never()).update(null);
	}
	
	@Test
	void updateStudentNoExist() {
		when(studentDao.getById(student.getId())).thenReturn(null);
		assertThrows(IllegalArgumentException.class,() -> {studentService.update(student);},"If argument didn't correct id should throw IllegalArgumentException");
		verify(studentDao).getById(student.getId());
		verify(studentDao,never()).update(student);
	}
	
	@Test
	void deleteNoExistStudent() {
		when(studentDao.getById(student.getId())).thenReturn(null);
		assertThrows(IllegalArgumentException.class,() -> {studentService.delete(student.getId());},"Id didn't exist, so throw IllegalArgumentException");
		verify(studentDao).getById(student.getId());
		verify(studentDao,never()).delete(student.getId());
	}
	
	@Test
	void deleteExistStudent() {
		when(studentDao.getById(student.getId())).thenReturn(student);
		when(studentDao.delete(student.getId())).thenReturn(1);
		assertEquals(1, studentService.delete(student.getId()));
		verify(studentDao).getById(student.getId());
		verify(studentDao).delete(student.getId());
	}
	
	@Test
	void getByEmailNoExist() {
		when(studentDao.getByEmail(student.getEmail())).thenReturn(null);
		assertNull(studentService.getByEmail(student.getEmail()),"Email don't exist so, we get null");
		verify(studentDao).getByEmail(student.getEmail());
	}
	
	@Test
	void getByEmailExist() {
		when(studentDao.getByEmail(student.getEmail())).thenReturn(student);
		assertNotNull(studentService.getByEmail(student.getEmail()),"We should get student object");
		verify(studentDao).getByEmail(student.getEmail());
	}
	
	@Test
	void getByFavoriteLanguageNoExistReturnNull() {
		when(studentDao.getByfavoriteLanguage(student.getFavoriteLanguage())).thenReturn(null);
		assertNull(studentService.favoriteLanguage(student.getFavoriteLanguage()));
		verify(studentDao).getByfavoriteLanguage(student.getFavoriteLanguage());
	}
	
	@Test
	void getByFavoriteLanguageExistReturnNotNull() {
		List<Student> students = new ArrayList<>();
		students.add(student);
		when(studentDao.getByfavoriteLanguage(student.getFavoriteLanguage())).thenReturn(students);
		assertNotNull(studentService.favoriteLanguage(student.getFavoriteLanguage()));
		verify(studentDao).getByfavoriteLanguage(student.getFavoriteLanguage());
	}
	
}




