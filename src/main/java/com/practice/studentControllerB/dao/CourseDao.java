package com.practice.studentControllerB.dao;

import java.util.List;

import com.practice.studentControllerB.model.Course;

public interface CourseDao extends GenericDao<Course> {
	
	Course getByTitle(String title);
	
	List<Course> getByShift(String shift);
	
	Course getByTeacherId(Long teacher);
}
