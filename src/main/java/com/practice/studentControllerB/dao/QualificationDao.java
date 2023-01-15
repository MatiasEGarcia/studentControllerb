package com.practice.studentControllerB.dao;

import java.util.List;

import com.practice.studentControllerB.model.Qualification;

public interface QualificationDao extends GenericDao<Qualification> {

	List<Qualification> getByCourseId(Long Course);
	
	List<Qualification> getByStudentId(Long student);
	
}
