package com.practice.studentControllerB.dao.de;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.practice.studentControllerB.model.Course;
import com.practice.studentControllerB.model.Teacher;

public class CourseResultSetExtractor implements ResultSetExtractor<List<Course>>{

	@Override
	public List<Course> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Course> courses = null;
		Course course;
		Teacher teacher;
		while(rs.next()) {
			courses = new ArrayList<>();
			course = new Course();
			teacher = new Teacher();
			course.setId(rs.getLong("id"));
			course.setTitle(rs.getString("Title"));
			course.setShift(rs.getString("shift"));
			teacher.setId(rs.getLong("teacher"));
			course.setTeacher(teacher);
			courses.add(course);
		}
		return courses;
	}

}
