package com.practice.studentControllerB.dao;

import java.util.List;

public interface GenericDao<T> {
	
	List<T> getAll();
	
	int create(T t);
	
	T getById();
	
	int update(T t);
	
	int delete(Long id);

}
