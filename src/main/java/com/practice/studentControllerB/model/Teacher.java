package com.practice.studentControllerB.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
public class Teacher extends Person{

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message= "{vali.teacher.qualification-not-blank}")
	private String qualification; //enum
	
	@NotBlank(message= "{vali.teacher.nationality-not-blank}")
	private String nationality;
}
