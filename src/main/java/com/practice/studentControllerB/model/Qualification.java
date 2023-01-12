package com.practice.studentControllerB.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Qualification {
	
	private long id;
	private byte Qualification; 
	private Course course;
	private Student student;
}
