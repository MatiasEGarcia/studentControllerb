package com.practice.studentControllerB.dao.de;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.practice.studentControllerB.model.Course;
import com.practice.studentControllerB.model.Qualification;
import com.practice.studentControllerB.model.Student;

public class QualificationResultSerExtractor implements ResultSetExtractor<List<Qualification>>{

	@Override
	public List<Qualification> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Qualification> qualifications = null;
		Qualification qualification;
		Course course;
		Student student;
		
		while(rs.next()) {
			qualifications = new ArrayList<>();
			qualification = new Qualification();
			course = new Course();
			student = new Student();
			
			qualification.setId(rs.getLong("id"));
			qualification.setQualification(rs.getByte("qualification"));
			course.setId(rs.getLong("course"));
			student.setId(rs.getLong("student"));
			qualification.setStudent(student);
			qualification.setCourse(course);
			
			qualifications.add(qualification);
		}
		
		return qualifications;
	}

}
