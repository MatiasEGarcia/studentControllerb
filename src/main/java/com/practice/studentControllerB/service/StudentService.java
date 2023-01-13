package com.practice.studentControllerB.service;

import java.util.List;

import com.practice.studentControllerB.model.Student;

public interface StudentService extends GenericService<Student> {

	Student getByEmail(String email);

	List<Student> favoriteLanguage(String string);

}
