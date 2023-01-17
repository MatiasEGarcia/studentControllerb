package com.practice.studentControllerB.config.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PropertySource("classpath:controller-messages.properties")
@Configuration
@ConfigurationProperties("controller")
public class ControllerProp {

	private String noStudents;
}
