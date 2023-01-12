package com.practice.studentControllerB.dao;

import java.util.List;

import com.practice.studentControllerB.model.Student;

public interface StudentDao extends GenericDao<Student>{

	Student getByEmail(String email);
	
	List<Student> favoriteLanguage(String string);
}
