package com.practice.studentControllerB.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

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
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	
	@NotBlank(message= "{vali.person.name-not-blank}")
	private String name;
	
	@NotBlank(message= "{vali.person.lastname-not-blank}")
	private String lastname;
	
	@NotBlank(message= "{vali.person.email-not-blank}")
	private String email;
	
	@NotNull(message= "{vali.person.age-not-null}")
	@Range(max = 70, min = 18, message ="{vali.person.age-max-min}")
	private byte age;
}
