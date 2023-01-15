package com.practice.studentControllerB.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.practice.studentControllerB.dao.de.CourseResultSetExtractor;
import com.practice.studentControllerB.dao.de.CourseRowMapper;
import com.practice.studentControllerB.model.Course;

@Repository
public class CourseDaoImpl implements CourseDao{

	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public List<Course> getAll() {
		String sql = "SELECT * FROM courses";
		return jdbc.query(sql, new CourseResultSetExtractor());
	}

	@Override
	public int create(Course t) {
		String sql = "INSERT INTO courses (title,teacher,shift) VALUES (?,?,?)";
		Object[] args = {t.getTitle(),t.getTeacher().getId(),t.getShift()};
		return jdbc.update(sql,args);
	}

	@Override
	public Course getById(Long id) {
		Course course;
		String sql = "SELECT * FROM courses WHERE id = ?";
		try {
			course = jdbc.queryForObject(sql, new CourseRowMapper(),id);
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		return course;
	}

	@Override
	public int update(Course t) {
		String sql = "UPDATE courses SET title = ?, teacher = ?, shift = ? WHERE id = ?";
		Object[] args = {t.getTitle(),t.getTeacher().getId(),t.getShift(),t.getId()};
		return jdbc.update(sql,args);
	}

	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM courses WHERE id = ?";
		return jdbc.update(sql, id);
	}

	@Override
	public Course getByTitle(String title) {
		Course course;
		String sql = "SELECT * FROM courses WHERE title = ?";
		try {
			course = jdbc.queryForObject(sql, new CourseRowMapper(),title);
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		return course;
	}

	@Override
	public List<Course> getByShift(String shift) {
		String sql = "SELECT * FROM courses WHERE shift = ?";
		return jdbc.query(sql, new CourseResultSetExtractor(),shift);
	}

	@Override
	public Course getByTeacherId(Long teacher) {
		Course course;
		String sql = "SELECT * FROM courses WHERE teacher = ?";
		try {
			course = jdbc.queryForObject(sql, new CourseRowMapper(),teacher);
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		return course;
	}

}
