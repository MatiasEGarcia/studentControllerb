package com.practice.studentControllerB.model;

public enum Shift {
	MORNING, NIGHT;

	public boolean sameThan(String string) {
		return this.toString().equals(string);
	}
}
