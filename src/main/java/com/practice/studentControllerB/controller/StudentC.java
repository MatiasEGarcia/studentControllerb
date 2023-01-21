package com.practice.studentControllerB.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
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

import com.practice.studentControllerB.model.Student;
import com.practice.studentControllerB.payload.response.Message;
import com.practice.studentControllerB.service.StudentService;
import com.practice.studentControllerB.utils.MessagesProp;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/studentC")
@CrossOrigin("*")
public class StudentC {

	@Autowired
	private StudentService studentS;
	
	@Autowired
	private MessagesProp messagesProp;
	
	@GetMapping(value="/getAll")
	public ResponseEntity<?> getAll(){
		List<Student> students = studentS.getAll();
		if(students == null) {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("contr.there-no-students"))
					,HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(students,HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/getById/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id){
		Student student = studentS.getById(id);
		if(student == null) {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("e.student-id-not-found"))
					,HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(student,HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/create")
	public ResponseEntity<?> create(@Valid @RequestBody Student student){
		Student studentToCreate = new Student();
		BeanUtils.copyProperties(student, studentToCreate);
		studentS.create(studentToCreate);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping(value="/update")
	public ResponseEntity<?> update(@Valid @RequestBody Student student){
		if(studentS.update(student) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("contr.none-resource-updated"))
					,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		if(studentS.delete(id) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("contr.none-resource-deleted"))
					,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/getByEmail/{email}")
	public ResponseEntity<?> getByEmail(@PathVariable("email") String email){
		Student student = studentS.getByEmail(email);
		if(student == null) {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("contr.student-email-not-found"))
					,HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(student,HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/getByFavLanguage/{favLanguage}")
	public ResponseEntity<?> getByfavLanguage(@PathVariable("favLanguage") String favLanguage){
		List<Student> students = studentS.getByFavoriteLanguage(favLanguage);
		if(students == null) {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("contr.there-no-students"))
					,HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(students,HttpStatus.OK);
		}
	}
	
	
	
	
	
	
	
	
}
