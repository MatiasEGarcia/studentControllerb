package com.practice.studentControllerB.dao.de;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.practice.studentControllerB.model.Course;
import com.practice.studentControllerB.model.Teacher;

public class CourseRowMapper implements RowMapper<Course> {

	@Override
	public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
		Course course = new Course();
		Teacher teacher= new Teacher();
		course.setId(rs.getLong("id"));
		course.setTitle(rs.getString("Title"));
		course.setShift(rs.getString("shift"));
		teacher.setId(rs.getLong("teacher"));
		course.setTeacher(teacher);
		return course;
	}

}
