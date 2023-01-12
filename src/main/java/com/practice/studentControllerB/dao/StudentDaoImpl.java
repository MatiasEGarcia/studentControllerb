package com.practice.studentControllerB.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.practice.studentControllerB.model.Student;

@Repository
public class StudentDaoImpl implements StudentDao {

	@Override
	public List<Student> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Student t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Student getById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Student t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Student getByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> favoriteLanguage(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
