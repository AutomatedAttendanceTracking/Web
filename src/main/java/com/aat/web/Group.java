package com.aat.web;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Group {
	@Id public Long id;
	private int groupNumber;
	
	
	public Group() {
		this.groupNumber = -1;
	}
	public Group(int groupNumber) {
		this.groupNumber = groupNumber;
	}
	
	public int getGroupNumber() {
		return this.groupNumber;
	}
}
