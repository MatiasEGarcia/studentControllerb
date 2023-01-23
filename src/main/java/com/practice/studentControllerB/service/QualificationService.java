package com.practice.studentControllerB.service;

import java.util.List;

import com.practice.studentControllerB.model.Qualification;

public interface QualificationService extends GenericService<Qualification> {

	List<Qualification> getByCourseId(Long Course);

	List<Qualification> getByStudentId(Long student);
	
	void studentAndCourseExist(Qualification qualification);
}
