package com.practice.studentControllerB.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.practice.studentControllerB.dao.de.QualificationResultSerExtractor;
import com.practice.studentControllerB.dao.de.QualificationRowMapper;
import com.practice.studentControllerB.model.Qualification;

@Repository
public class QualificationDaoImpl implements QualificationDao{
	
	@Autowired
	private JdbcTemplate jdbc;

	@Override
	public List<Qualification> getAll() {
		String sql = "SELECT * FROM qualifications";
		return jdbc.query(sql, new QualificationResultSerExtractor());
	}

	@Override
	public int create(Qualification t) {
		String sql = "INSERT INTO qualifications (qualification, course, student) VALUES (?,?,?)";
		Object[] args = {t.getQualification(),t.getCourse().getId(), t.getStudent().getId()};
		return jdbc.update(sql,args);
	}

	@Override
	public Qualification getById(Long id) {
		Qualification qualification;
		String sql = "SELECT * FROM qualifications WHERE id = ?";
		try {
			qualification = jdbc.queryForObject(sql, new QualificationRowMapper(),id);
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		return qualification;
	}

	@Override
	public int update(Qualification t) {
		String sql = "UPDATE qualifications SET qualification = ?, course = ?, student = ? WHERE id = ?";
		Object[] args = {t.getQualification(),t.getCourse().getId(),t.getStudent().getId(),t.getId()};
		return jdbc.update(sql,args);
	}

	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM qualifications WHERE id = ?";
		return jdbc.update(sql,id);
	}

	@Override
	public List<Qualification> getByCourseId(Long course) {
		String sql = "SELECT * FROM qualifications WHERE course = ?";
		return jdbc.query(sql, new QualificationResultSerExtractor(),course);
	}

	@Override
	public List<Qualification> getByStudentId(Long student) {
		String sql = "SELECT * FROM qualifications WHERE student = ?";
		return jdbc.query(sql, new QualificationResultSerExtractor(),student);
	}

}
