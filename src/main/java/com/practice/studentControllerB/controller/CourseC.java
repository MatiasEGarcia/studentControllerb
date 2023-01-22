package com.practice.studentControllerB.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.studentControllerB.model.Course;
import com.practice.studentControllerB.payload.response.Message;
import com.practice.studentControllerB.service.CourseService;
import com.practice.studentControllerB.utils.MessagesProp;

@RestController
@RequestMapping("/courseC")
@CrossOrigin("*")
public class CourseC {

	@Autowired
	private CourseService courseS;
	
	@Autowired
	private MessagesProp messagesProp;
	
	@GetMapping(value="/getAll")
	public ResponseEntity<?> getAll(){
		List<Course> courses = courseS.getAll();
		if(courses == null) {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("there-no-courses"))
					,HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(courses,HttpStatus.OK);
		}
		
	}
	
}