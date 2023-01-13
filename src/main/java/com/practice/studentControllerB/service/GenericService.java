package com.practice.studentControllerB.service;

import java.util.List;

public interface GenericService <T>{

	List<T> getAll();
	
	int create(T t);
	
	T getById(Long id);
	
	int update(T t);
	
	int delete(Long id);
}
