package com.practice.studentControllerB.model;

import java.util.Calendar;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
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
@ConfigurationProperties("vali.student")
@Validated
public class Student extends Person{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "{addmissionDate.not.null}")
	private Calendar  addmissionDate;
	private String favoriteLanguage;
}
