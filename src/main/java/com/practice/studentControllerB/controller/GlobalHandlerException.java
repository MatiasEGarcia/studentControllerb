package com.practice.studentControllerB.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	
	@ExceptionHandler(value= {MethodArgumentNotValidException.class})
	public ResponseEntity<?> hanldeValidateExceptions(MethodArgumentNotValidException ex){
		Map<String , String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
	}
}
