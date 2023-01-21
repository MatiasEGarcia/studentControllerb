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

import com.practice.studentControllerB.model.Teacher;
import com.practice.studentControllerB.payload.response.Message;
import com.practice.studentControllerB.service.TeacherService;
import com.practice.studentControllerB.utils.MessagesProp;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/teacherC")
@CrossOrigin("*")
public class TeacherC {

	@Autowired
	private TeacherService teacherS;
	
	@Autowired
	private MessagesProp messagesProp;
	
	@GetMapping(value="/getAll")
	public ResponseEntity<?> getAll(){
		List<Teacher> teachers = teacherS.getAll();
		if(teachers == null) {
			return new ResponseEntity<>(new Message(messagesProp.getMessage("there-no-teachers"))
					,HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(teachers,HttpStatus.OK);
		}
	}
	@GetMapping(value="/getById/{id}")
	public ResponseEntity<?> getById(@PathVariable("id")Long id){
		Teacher teacher = teacherS.getById(id);
		if(teacher == null) {
			return new ResponseEntity<>(new Message(messagesProp.getMessage("teacher-id-not-found"))
					,HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(teacher,HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/create")
	public ResponseEntity<?> create(@Valid @RequestBody Teacher teacher){
		if(teacherS.create(teacher) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("none-resource-created"))
					,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value="/update")
	public ResponseEntity<?> update(@Valid @RequestBody Teacher teacher){
		if(teacherS.update(teacher) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("none-resource-updated"))
					,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id")Long id){
		if(teacherS.delete(id) == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("none-resource-deleted"))
					,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/getByQualification/{qualific}")
	public ResponseEntity<?> getByQualification(@PathVariable("qualific") String qualification){
		List<Teacher> teachers = teacherS.getByQualification(qualification);
		if(teachers == null) {
			return new ResponseEntity<>(new Message(messagesProp.getMessage("there-no-teachers"))
					,HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(teachers,HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/getByNationality/{nationality}")
	public ResponseEntity<?> getByNatinality(@PathVariable("nationality") String nationality){
		List<Teacher> teachers = teacherS.getByNationality(nationality);
		if(teachers == null) {
			return new ResponseEntity<>(new Message(messagesProp.getMessage("there-no-teachers"))
					,HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(teachers,HttpStatus.OK);
		}
	}
}
