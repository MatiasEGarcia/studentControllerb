package com.practice.studentControllerB.model;

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

	String qualification; //enum
	String nationality;
}