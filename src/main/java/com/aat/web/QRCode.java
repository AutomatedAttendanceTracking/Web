package com.aat.web;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class QRCode {
	@Id
	public Long id;
	@Parent
	private Key<Student> student;
	private Date timestamp;
	private int randValue;
	
	public QRCode() {
		timestamp = null;
		randValue = 0;
	}
	
	public QRCode(int studentNumber, Date time, int random) {
		if( studentNumber != 0 ) {
			this.student = Key.create(Student.class, Integer.toString(studentNumber));  // Creating the Ancestor key
		} else {
			this.student = Key.create(Student.class, "default");
		}
		this.timestamp = time;
		this.randValue = random;
	}
	
	public Date getTimestamp() { return timestamp; }
	public int getRandValue() { return randValue; }
	
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
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof QRCode)) return false;
	    QRCode pairo = (QRCode) o;
	    return this.timestamp.equals(pairo.getTimestamp()) &&
	           this.randValue == pairo.getRandValue();
	}
}
