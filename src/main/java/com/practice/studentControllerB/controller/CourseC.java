package com.practice.studentControllerB.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.studentControllerB.model.Course;
import com.practice.studentControllerB.payload.response.Message;
import com.practice.studentControllerB.service.CourseService;
import com.practice.studentControllerB.utils.MessagesProp;

import jakarta.validation.Valid;

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
	
	@PostMapping(value="/create")
	public ResponseEntity<?> create(@Valid @RequestBody Course course){
		if(courseS.create(course) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("none-resource-created"))
					,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value="/update")
	public ResponseEntity<?> update(@Valid @RequestBody Course course){
		if(courseS.update(course) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("none-resource-updated"))
					,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		if(courseS.delete(id) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("none-resource-updated"))
					,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/getByTitle/{title}")
	public ResponseEntity<?> getByTitle(@PathVariable("title") String title){
		Course course = courseS.getByTitle(title);
		if(course == null) return new ResponseEntity<>(new Message(messagesProp.getMessage("course-title-not-found")),HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(course,HttpStatus.OK);
	}
	
	@GetMapping(value="/getByShift/{shift}")
	public ResponseEntity<?> getByShift(@PathVariable("shift") String shift){
		List<Course> courses = courseS.getByShift(shift);
		if(courses == null) return new ResponseEntity<>(new Message(messagesProp.getMessage("there-no-courses")),HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(courses,HttpStatus.OK);
	}
	
	@GetMapping(value="/getByTeacherId/{id}")
	public ResponseEntity<?> getByTeacherId(@PathVariable("id") Long id){
		Course course = courseS.getByTeacherId(id);
		if(course == null) return new ResponseEntity<>(new Message(messagesProp.getMessage("there-no-courses")),HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(course,HttpStatus.OK);
	}
}
	
	
	
	
	
	
	
	
	
	
	
