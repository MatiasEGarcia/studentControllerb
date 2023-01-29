package com.practice.studentControllerB.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import com.practice.studentControllerB.model.Qualification;
import com.practice.studentControllerB.payload.response.Message;
import com.practice.studentControllerB.service.QualificationService;
import com.practice.studentControllerB.utils.MessagesProp;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/qualificationC")
@CrossOrigin("*")
public class QualificationC {

	@Autowired
	private QualificationService qualificationS;
	
	@Autowired
	private MessagesProp messagesProp;
	
	@GetMapping(value="/getAll")
	public ResponseEntity<?> getAll(){
		HttpHeaders headers;
		List<Qualification> qualifications = qualificationS.getAll();
		if(qualifications == null) {
			headers = new HttpHeaders();
			headers.add("Info-Header", messagesProp.getMessage("there-no-qualifications"));
			return new ResponseEntity<>(headers,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(qualifications, HttpStatus.OK);
	}
	
	@PostMapping(value="/create")
	public ResponseEntity<?> create(@Valid @RequestBody Qualification qualification){
		if(qualificationS.create(qualification) == 0) {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("none-resource-created"))
					,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/getById/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id){
		Qualification qualification = qualificationS.getById(id);
		if(qualification == null) {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("qualification-id-not-found"))
					,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(qualification,HttpStatus.OK);
	}
	
	@PutMapping(value="/update")
	public ResponseEntity<?> update(@Valid @RequestBody Qualification qualification){
		if(qualificationS.update(qualification) == 0) {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("none-resource-updated"))
					,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		if(qualificationS.delete(id)==0) {
			return new ResponseEntity<>(
					new Message(messagesProp.getMessage("none-resource-deleted"))
					,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/getByCourseId/{id}")
	public ResponseEntity<?> getByCourseId(@PathVariable("id") Long id){
		HttpHeaders headers;
		List<Qualification> qualifications = qualificationS.getByCourseId(id);
		if(qualifications == null) {
			headers = new HttpHeaders();
			headers.add("Info-Header", messagesProp.getMessage("there-no-qualifications"));
			return new ResponseEntity<>(headers,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(qualifications,HttpStatus.OK);
		
	}
	
	@GetMapping(value="/getByStudentId/{id}")
	public ResponseEntity<?> getByStudentId(@PathVariable("id") Long id){
		HttpHeaders headers;
		List<Qualification> qualifications = qualificationS.getByStudentId(id);
		if(qualifications == null) {
			headers = new HttpHeaders();
			headers.add("Info-header", messagesProp.getMessage("there-no-qualifications"));
			return new ResponseEntity<>(headers,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(qualifications,HttpStatus.OK);
	}
}
