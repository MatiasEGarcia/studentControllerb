package com.practice.studentControllerB.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.practice.studentControllerB.payload.response.Message;

@RestControllerAdvice
public class GlobalHandlerException {

	@ExceptionHandler(value= {IllegalArgumentException.class})
	public ResponseEntity<?> IllegalArgumentExeptionHandler(IllegalArgumentException ex){
		Message mes = new Message(ex.getMessage());
		return new ResponseEntity<>(mes,HttpStatus.BAD_REQUEST);
	}
	
}
