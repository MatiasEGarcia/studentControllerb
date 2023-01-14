package com.practice.studentControllerB.dao.de;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.practice.studentControllerB.model.Teacher;

public class TeacherResultSetExtractor implements ResultSetExtractor<List<Teacher>> {

	@Override
	public List<Teacher> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Teacher> teachers = null;
		Teacher teacher;
		while(rs.next()) {
			teachers = new ArrayList<>();
			teacher = new Teacher();
			teacher.setId(rs.getLong("id"));
			teacher.setName(rs.getString("name"));
			teacher.setLastname(rs.getString("lastname"));
			teacher.setEmail(rs.getString("email"));
			teacher.setAge(rs.getByte("age"));
			teacher.setNationality(rs.getString("nationality"));
			teacher.setQualification(rs.getString("qualification"));
			teachers.add(teacher);
		}
		return teachers;
	}

}
