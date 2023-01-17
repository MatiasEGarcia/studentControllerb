package com.practice.studentControllerB.config.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PropertySource("classpath:exception-messages.properties")
@Configuration
@ConfigurationProperties("e")
public class ExceptionProp {

	private String studentNotNull;
	
	private String studentEmailAlreadyUsed;
	
	private String studentIdNotFound;
	
	private String teacherNotNull;
	
	private String teacherEmailAlreadyUsed;
	
	private String teacherIdNotFound;
	
	private String courseNotnull;
	
	private String courseIdNotFound;
	
	private String courseTitleAlreadyUsed;
	
	private String shiftNotFound;
	
	private String qualificationNotNull;
	
	private String qualificationIdNotFound;
}
