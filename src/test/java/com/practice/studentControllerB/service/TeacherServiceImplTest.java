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

import com.practice.studentControllerB.config.prop.ExceptionProp;
import com.practice.studentControllerB.dao.TeacherDao;
import com.practice.studentControllerB.model.QualificationE;
import com.practice.studentControllerB.model.Teacher;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

	@Mock private ExceptionProp excepProp;
	@Mock private TeacherDao teacherDao;
	@InjectMocks private TeacherServiceImpl teacherService;
	List<Teacher> teachers;
	Teacher teacher;
	
	@BeforeEach
	void setUpTeacher() {
		teachers = new ArrayList<>();
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
		when(teacherDao.getAll()).thenReturn(teachers);
		assertNotNull(teacherService.getAll());
		verify(teacherDao).getAll();
	}
	
	@Test
	void createArgumentNull() {
		assertThrows(IllegalArgumentException.class, () -> {teacherService.create(null);});
		verify(teacherDao,never()).create(null);
	}

	@Test
	void createEmailAlreayUsed() {
		when(teacherDao.getByEmail(teacher.getEmail())).thenReturn(teacher);
		assertThrows(IllegalArgumentException.class, () -> {teacherService.create(teacher);});
		verify(teacherDao).getByEmail(teacher.getEmail());
		verify(teacherDao,never()).create(teacher);
	}
	
	@Test
	void createReturn1() {
		when(teacherDao.getByEmail(teacher.getEmail())).thenReturn(null);
		when(teacherDao.create(teacher)).thenReturn(1);
		assertEquals(1, teacherService.create(teacher));
		
		verify(teacherDao).getByEmail(teacher.getEmail());
		verify(teacherDao).create(teacher);
	}
	
	@Test
	void getByIdReturnNull() {
		assertNull(teacherService.getById(teacher.getId()));
		verify(teacherDao).getById(teacher.getId());
	}
	
	@Test
	void getByIdReturnNotNull() {
		when(teacherDao.getById(teacher.getId())).thenReturn(teacher);
		assertNotNull(teacherService.getById(teacher.getId()));
		verify(teacherDao).getById(teacher.getId());
	}
	
	@Test
	void updateArgumentNull() {
		assertThrows(IllegalArgumentException.class, () -> {teacherService.update(null);});
		verify(teacherDao,never()).update(null);
	}
	
	@Test
	void updateTeacherWithIdNoExist() {
		when(teacherDao.getById(teacher.getId())).thenReturn(null);
		assertThrows(IllegalArgumentException.class, () -> {teacherService.update(teacher);});
		verify(teacherDao).getById(teacher.getId());
		verify(teacherDao,never()).update(teacher);
	}
	
	@Test
	void updateReturn1() {
		when(teacherDao.getById(teacher.getId())).thenReturn(teacher);
		when(teacherDao.update(teacher)).thenReturn(1);
		assertEquals(1, teacherService.update(teacher));
		verify(teacherDao).getById(teacher.getId());
		verify(teacherDao).update(teacher);
	}
	
	@Test
	void deleteTeacherNoExist() {
		when(teacherDao.getById(teacher.getId())).thenReturn(null);
		assertThrows(IllegalArgumentException.class, () -> {teacherService.delete(teacher.getId());});
		verify(teacherDao).getById(teacher.getId());
		verify(teacherDao,never()).delete(teacher.getId());
	}
	
	@Test
	void deleteTeacherReturn1() {
		when(teacherDao.getById(teacher.getId())).thenReturn(teacher);
		when(teacherDao.delete(teacher.getId())).thenReturn(1);
		assertEquals(1, teacherService.delete(teacher.getId()));
		verify(teacherDao).getById(teacher.getId());
		verify(teacherDao).delete(teacher.getId());
	}
	
	@Test
	void getByQualificationReturnNull() {
		when(teacherDao.getByQualification(teacher.getQualification())).thenReturn(null);
		assertNull(teacherService.getByQualification(teacher.getQualification()));
		verify(teacherDao).getByQualification(teacher.getQualification());
	}
	
	@Test
	void getByQualificationReturnNotNull() {
		when(teacherDao.getByQualification(teacher.getQualification())).thenReturn(teachers);
		assertNotNull(teacherService.getByQualification(teacher.getQualification()));
		verify(teacherDao).getByQualification(teacher.getQualification());
	}
	
	@Test
	void getByNationalityReturnNull() {
		when(teacherDao.getByNationality(teacher.getNationality())).thenReturn(null);
		assertNull(teacherService.getByNationality(teacher.getNationality()));
		verify(teacherDao).getByNationality(teacher.getNationality());
	}
	
	@Test
	void getByNationalityReturnNotNull() {
		when(teacherDao.getByNationality(teacher.getNationality())).thenReturn(teachers);
		assertNotNull(teacherService.getByNationality(teacher.getNationality()));
		verify(teacherDao).getByNationality(teacher.getNationality());
	}
	
	
	
}
