package com.practice.studentControllerB.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
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
public class Qualification implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	@Range(min=1 , max =10, message="{vali.qualification-quali-min-max}")
	private byte qualification; 
	
	@NotNull(message= "{vali.qualification-course-not-null}")
	private Course course;
	
	@NotNull(message= "{vali.qualification-student-not-null}")
	private Student student;
}
