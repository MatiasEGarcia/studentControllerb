package com.practice.studentControllerB.payload.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
@Setter
public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	
	String message;
}
