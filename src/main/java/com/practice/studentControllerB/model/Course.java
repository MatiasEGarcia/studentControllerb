package com.practice.studentControllerB.model;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
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
public class Course implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message="{vali.course-title-not-blank}")
	private String title;
	
	@NotBlank(message="{vali.course-shift-not-blank}")
	private String shift; 
	
	@NotNull(message="{vali.course-teacher-not-null}")
	private Teacher teacher;
}
