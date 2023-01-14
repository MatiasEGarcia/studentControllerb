package com.practice.studentControllerB.dao;

import java.util.List;

import com.practice.studentControllerB.model.Teacher;

public interface TeacherDao extends GenericDao<Teacher>{

	List<Teacher> getByQualification(String qualification);
	
	List<Teacher> getByNationality(String nationality);
}
