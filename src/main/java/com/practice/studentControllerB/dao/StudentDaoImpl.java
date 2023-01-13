package com.practice.studentControllerB.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.practice.studentControllerB.dao.de.StudentResultSetExtractor;
import com.practice.studentControllerB.model.Student;

@Repository
public class StudentDaoImpl implements StudentDao {

	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public List<Student> getAll() {
		String sql = "SELECT * FROM students";
		return jdbc.query(sql, new StudentResultSetExtractor());
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
