package com.aat.web;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Group {
	@Id public String id;
	private Long groupNumber;
	
	public Group(Long groupNumber) {
		this.groupNumber = groupNumber;
	}
	
	public Long getGroupNumber() {
		return this.groupNumber;
	}
}