package com.practice.studentControllerB.dao.de;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.springframework.jdbc.core.RowMapper;

import com.practice.studentControllerB.model.Student;

public class StudentRowMapper implements RowMapper<Student> {

	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		Student student = new Student();
		Calendar calendar = Calendar.getInstance();
		student = new Student();
		student.setId(rs.getLong("id"));
		student.setName(rs.getString("name"));
		student.setLastname(rs.getString("lastname"));
		student.setEmail(rs.getString("email"));
		student.setAge(rs.getByte("age"));
		calendar.setTime(rs.getDate("admission_date"));
		student.setAddmissionDate(calendar);
		student.setFavoriteLanguage(rs.getString("favorite_language"));
		return student;
	}

}
