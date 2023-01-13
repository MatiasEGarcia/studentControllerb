package com.practice.studentControllerB.model;

import java.util.Calendar;

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
public class Student extends Person{

	private Calendar  addmissionDate;
	private String favoriteLanguage;
}
