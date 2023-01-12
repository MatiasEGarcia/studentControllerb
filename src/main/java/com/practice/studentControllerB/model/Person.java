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
public class Person {

	private long id;
	private String name;
	private String apellido;
	private String correo;
	private byte edad;
}
