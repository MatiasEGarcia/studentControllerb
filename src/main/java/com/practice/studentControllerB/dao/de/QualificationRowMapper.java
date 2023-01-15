package com.practice.studentControllerB.dao.de;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.practice.studentControllerB.model.Course;
import com.practice.studentControllerB.model.Qualification;
import com.practice.studentControllerB.model.Student;

public class QualificationRowMapper implements RowMapper<Qualification>{

	@Override
	public Qualification mapRow(ResultSet rs, int rowNum) throws SQLException {
		Qualification qualification = new Qualification();
		Course course = new Course();
		Student student = new Student();
		
		qualification.setId(rs.getLong("id"));
		qualification.setQualification(rs.getByte("qualification"));
		course.setId(rs.getLong("course"));
		student.setId(rs.getLong("student"));
		qualification.setStudent(student);
		qualification.setCourse(course);
		return qualification;
	}

}
