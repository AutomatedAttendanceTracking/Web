package com.aat.web;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Participation {
	@Parent private Key<Student> student;
	@Id public Long id;
	
	private Date date;
	
	public Participation() {
		this.date = null;
	}
	
	public Participation(int studentNumber, Date date) {
		if( studentNumber != 0 ) {
			this.student = Key.create(Student.class, Integer.toString(studentNumber));  // Creating the Ancestor key
		} else {
			this.student = Key.create(Student.class, "default");
		}
		this.date = date;
	}
	
	public Date getDate() { return date; }
	
	public Key<Student> getStudent() {
		return this.student;
	}
	
	public void setStudent(int studentNumber) {
		if( studentNumber != 0 ) {
			this.student = Key.create(Student.class, Integer.toString(studentNumber));  // Creating the Ancestor key
		} else {
			this.student = Key.create(Student.class, "default");
		}
	}
	
	
}
