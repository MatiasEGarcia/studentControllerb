package com.practice.studentControllerB.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.practice.studentControllerB.dao.de.StudentResultSetExtractor;
import com.practice.studentControllerB.dao.de.StudentRowMapper;
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
		String sql = "INSERT INTO students (name,lastname,email,age,admission_date, favorite_language) VALUES (?,?,?,?,?,?)";
		Object[] args = {t.getName(),t.getLastname(),t.getEmail(),t.getAge(),t.getAddmissionDate().getTime(),t.getFavoriteLanguage()};
		return jdbc.update(sql,args);
	}

	@Override
	public Student getById(Long id) {
		Student student;
		String sql = "SELECT * FROM students WHERE id = ?";
		try {
			student = jdbc.queryForObject(sql, new StudentRowMapper(),id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
		return student;
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
