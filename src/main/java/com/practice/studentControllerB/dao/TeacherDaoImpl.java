package com.practice.studentControllerB.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.practice.studentControllerB.dao.de.TeacherResultSetExtractor;
import com.practice.studentControllerB.dao.de.TeacherRowMapper;
import com.practice.studentControllerB.model.Teacher;

@Repository
public class TeacherDaoImpl implements TeacherDao{
	
	@Autowired
	private JdbcTemplate jdbc;

	@Override
	public List<Teacher> getAll() {
		String sql = "SELECT * FROM teachers";
		return jdbc.query(sql, new TeacherResultSetExtractor());
	}

	@Override
	public int create(Teacher t) {
		String sql = "INSERT INTO teachers (name, lastname,email,age,qualification,nationality) VALUES (?,?,?,?,?,?)";
		Object[] args = {t.getName(),t.getLastname(),t.getEmail(),t.getAge(),t.getQualification(),t.getNationality()};
		return jdbc.update(sql,args);
	}

	@Override
	public Teacher getById(Long id) {
		Teacher teacher;
		String sql = "SELECT * FROM teachers WHERE id = ?";
		try {
			teacher = jdbc.queryForObject(sql, new TeacherRowMapper(),id);
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		return teacher;
	}

	@Override
	public int update(Teacher t) {
		String sql = "UPDATE teachers SET name=?, lastname=?, email=?, age=?,qualification=?,nationality=? WHERE id=?";
		Object[] args = {t.getName(),t.getLastname(),t.getEmail(),t.getAge(),t.getQualification(),t.getNationality(),t.getId()};
		return jdbc.update(sql,args);
	}

	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM teachers WHERE id = ?";
		return jdbc.update(sql,id);
	}

	@Override
	public List<Teacher> getByQualification(String qualification) {
		String sql = "SELECT * FROM teachers WHERE qualification=?";
		return jdbc.query(sql, new TeacherResultSetExtractor(),qualification);
	}

	@Override
	public List<Teacher> getByNationality(String nationality) {
		String sql = "SELECT * FROM teachers WHERE nationality=?";
		return jdbc.query(sql, new TeacherResultSetExtractor(),nationality);
	}

}
