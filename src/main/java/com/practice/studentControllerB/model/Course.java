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
public class Course {

	private String titulo;
	private String shift; 
	private Teacher teacher;
}
