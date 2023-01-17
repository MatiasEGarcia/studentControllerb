package com.practice.studentControllerB.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.studentControllerB.config.prop.ControllerProp;
import com.practice.studentControllerB.model.Student;
import com.practice.studentControllerB.payload.response.Message;
import com.practice.studentControllerB.service.StudentService;

import java.net.InetAddress;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/studentC")
@CrossOrigin("*")
public class StudentC {

	@Autowired
	private StudentService studentS;
	
	@Autowired
	private ControllerProp contProp;
	
	@GetMapping(value="/getAll")
	public ResponseEntity<?> getAll(){
		List<Student> students = studentS.getAll();
		if(students == null) {
			return new ResponseEntity<>(new Message(contProp.getNoStudents()),HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(students,HttpStatus.OK);
		}
	}
	
}
