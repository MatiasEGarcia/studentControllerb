package com.practice.studentControllerB.service;

import java.util.List;

import com.practice.studentControllerB.model.Teacher;

public interface TeacherService extends GenericService<Teacher> {

	List<Teacher> getByQualification(String qualification);

	List<Teacher> getByNationality(String nationality);
}
