package com.practice.studentControllerB.dao.de;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.practice.studentControllerB.model.Teacher;

public class TeacherRowMapper implements RowMapper<Teacher>{

	@Override
	public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
		Teacher teacher = new Teacher();
		teacher.setId(rs.getLong("id"));
		teacher.setName(rs.getString("name"));
		teacher.setLastname(rs.getString("lastname"));
		teacher.setEmail(rs.getString("email"));
		teacher.setAge(rs.getByte("age"));
		teacher.setNationality(rs.getString("nationality"));
		teacher.setQualification(rs.getString("qualification"));
		return teacher;
	}

}
