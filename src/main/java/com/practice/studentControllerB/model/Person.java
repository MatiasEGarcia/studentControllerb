package com.practice.studentControllerB.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
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
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	
	@NotBlank(message= "{vali.person.name-not-blank}")
	private String name;
	
	@NotBlank(message= "{vali.person.lastname-not-blank}")
	private String lastname;
	
	@NotBlank(message= "{vali.person.email-not-blank}")
	private String email;
	
	@Range( min = 18, max = 70, message ="{vali.person.age-max-min}")
	private byte age;
}
