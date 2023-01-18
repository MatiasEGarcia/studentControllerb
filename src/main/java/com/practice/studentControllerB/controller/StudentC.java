package com.practice.studentControllerB.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.studentControllerB.config.prop.ControllerProp;
import com.practice.studentControllerB.model.Student;
import com.practice.studentControllerB.payload.response.Message;
import com.practice.studentControllerB.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/studentC")
@CrossOrigin("*")
public class StudentC {

	@Autowired
	private StudentService studentS;
	
	@Autowired
	private ControllerProp contProp;//here I have the properties messages
	
	@GetMapping(value="/getAll")
	public ResponseEntity<?> getAll(){
		List<Student> students = studentS.getAll();
		if(students == null) {
			return new ResponseEntity<>(new Message(contProp.getNoStudents()),HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(students,HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/getById/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id){
		Student student = studentS.getById(id);
		if(student == null) {
			return new ResponseEntity<>(new Message(contProp.getStudentIdNotFound()),HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(student,HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/create")
	public ResponseEntity<?> create(@Valid @RequestBody Student student){
		Student studentToCreate = new Student();
		BeanUtils.copyProperties(student, studentToCreate);
		studentS.create(studentToCreate);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
