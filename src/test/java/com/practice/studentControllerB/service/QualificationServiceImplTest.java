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

import com.practice.studentControllerB.dao.QualificationDao;
import com.practice.studentControllerB.model.Course;
import com.practice.studentControllerB.model.Qualification;
import com.practice.studentControllerB.model.Student;

@ExtendWith(MockitoExtension.class)
class QualificationServiceImplTest {

	@Mock private QualificationDao qualificationDao;
	@InjectMocks private QualificationServiceImpl qualificationService;
	Qualification qualification;
	Course course;
	Student student;
	
	@BeforeEach
	void qualificationSetUp() {
		qualification = new Qualification();
		course = new Course();
		student = new Student();
	}
	
	
	@Test
	void getAllReturnNull() {
		when(qualificationDao.getAll()).thenReturn(null);
		assertNull(qualificationService.getAll());
		verify(qualificationDao).getAll();
	}

	@Test
	void getAllReturnNotNull() {
		List<Qualification> qualifications = new ArrayList<>();
		when(qualificationDao.getAll()).thenReturn(qualifications);
		assertNotNull(qualificationService.getAll());
		verify(qualificationDao).getAll();
	}
	
	@Test
	void createArgumentNullThorw() {
		assertThrows(IllegalArgumentException.class, () -> {qualificationService.create(null);});
		verify(qualificationDao,never()).create(null);
	}
	
	@Test
	void createReturn1() {
		when(qualificationDao.create(qualification)).thenReturn(1);
		assertEquals(1,qualificationService.create(qualification));
		verify(qualificationDao).create(qualification);
	}
	
	@Test
	void getByIdNoExistReturnNull() {
		qualification.setId(1L);
		when(qualificationDao.getById(qualification.getId())).thenReturn(null);
		assertNull(qualificationService.getById(qualification.getId()));
		verify(qualificationDao).getById(qualification.getId());
	}
	
	@Test
	void getByIdExistReturnEquals() {
		qualification.setId(1L);
		when(qualificationDao.getById(qualification.getId())).thenReturn(qualification);
		assertEquals(qualification,qualificationService.getById(qualification.getId()));
		verify(qualificationDao).getById(qualification.getId());
	}
	
	@Test
	void updateArgumentNullThrow() {
		assertThrows(IllegalArgumentException.class, () -> {qualificationService.update(null);});
		verify(qualificationDao,never()).update(null);
	}
	@Test
	void updateQualificationIdNoExistThrow() {
		qualification.setId(1L);
		when(qualificationDao.getById(qualification.getId())).thenReturn(null);
		assertThrows(IllegalArgumentException.class, () -> {qualificationService.update(qualification);});
		verify(qualificationDao).getById(qualification.getId());
		verify(qualificationDao,never()).update(qualification);
	}
	
	@Test
	void updateQualificationIdExist() {
		qualification.setId(1L);
		when(qualificationDao.getById(qualification.getId())).thenReturn(qualification);
		when(qualificationDao.update(qualification)).thenReturn(1);
		assertEquals(1, qualificationService.update(qualification));
		verify(qualificationDao).getById(qualification.getId());
		verify(qualificationDao).update(qualification);
	}
	
	@Test
	void deleteQualificationIdNoExistThrow() {
		qualification.setId(1L);
		when(qualificationDao.getById(qualification.getId())).thenReturn(null);
		assertThrows(IllegalArgumentException.class, () -> {qualificationService.delete(qualification.getId());});
		verify(qualificationDao).getById(qualification.getId());
		verify(qualificationDao,never()).delete(qualification.getId());
	}
	
	@Test
	void deleteQualificationIdExist() {
		qualification.setId(1L);
		when(qualificationDao.getById(qualification.getId())).thenReturn(qualification);
		when(qualificationDao.delete(qualification.getId())).thenReturn(1);
		assertEquals(1, qualificationService.delete(qualification.getId()));
		verify(qualificationDao).getById(qualification.getId());
		verify(qualificationDao).delete(qualification.getId());
	}
	
	@Test
	void getByCourseIdReturnNull() {
		course.setId(1L);
		when(qualificationDao.getByCourseId(course.getId())).thenReturn(null);
		assertNull(qualificationService.getByCourseId(course.getId()));
		verify(qualificationDao).getByCourseId(course.getId());
	}
	
	@Test
	void getByCourseIdReturnEquals() {
		List<Qualification> qualifications = new ArrayList<>();
		course.setId(1L);
		when(qualificationDao.getByCourseId(course.getId())).thenReturn(qualifications);
		assertEquals(qualifications,qualificationService.getByCourseId(course.getId()));
		verify(qualificationDao).getByCourseId(course.getId());
	}
	
	@Test
	void getByStudentIdReturnNull() {
		student.setId(1L);
		when(qualificationDao.getByStudentId(student.getId())).thenReturn(null);
		assertNull(qualificationService.getByStudentId(student.getId()));
		verify(qualificationDao).getByStudentId(student.getId());
	}
	
	@Test
	void getByStudentIdReturnEquals() {
		List<Qualification> qualifications = new ArrayList<>();
		student.setId(1L);
		when(qualificationDao.getByStudentId(student.getId())).thenReturn(qualifications);
		assertEquals(qualifications,qualificationService.getByStudentId(student.getId()));
		verify(qualificationDao).getByStudentId(student.getId());
	}
	
}
