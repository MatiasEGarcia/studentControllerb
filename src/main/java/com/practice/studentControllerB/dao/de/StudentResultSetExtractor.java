package com.practice.studentControllerB.dao.de; //dataExtractor

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.practice.studentControllerB.model.Student;

public class StudentResultSetExtractor implements ResultSetExtractor<List<Student>>{

	@Override
	public List<Student> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Student> studentsList = null;
		Calendar calendar;
		Student student;
		while(rs.next()) {
			studentsList = new ArrayList<>();
			calendar = Calendar.getInstance();
			student = new Student();
			student.setId(rs.getLong("id"));
			student.setName(rs.getString("name"));
			student.setLastname(rs.getString("lastname"));
			student.setEmail(rs.getString("email"));
			student.setAge(rs.getByte("age"));
			calendar.setTime(rs.getDate("admission_date"));
			student.setAddmissionDate(calendar);
			student.setFavoriteLanguage(rs.getString("favorite_language"));
			
			studentsList.add(student);
		}
		return studentsList;//The service will decide if it want to return Empty list,null or immutable list
	}

}
