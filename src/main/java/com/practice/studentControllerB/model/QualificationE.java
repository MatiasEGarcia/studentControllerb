package com.practice.studentControllerB.model;

public enum QualificationE {
	SECONDARY, UNIVERSITARY, TERTIARY, DOCTORATE;

	public boolean sameThan(String string) {
		return this.toString().equals(string);
	}
}
