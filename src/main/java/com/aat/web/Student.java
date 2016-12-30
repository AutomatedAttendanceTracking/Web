package com.aat.web;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Student {
	  @Parent private Key<Group> theGroup;
	  @Id public Long id;
	  private Long studentNumber;
	  private int personalQRCode;
	  private Date qrCodeTimeStamp;

	  /**
	   * Simple constructor just sets the date
	   **/
	  public Student(Group group, Long studentNumber) {
		  if( group != null ) {
			  this.theGroup = Key.create(group);  // Creating the Ancestor key
		  } else {
		      this.theGroup = Key.create(Group.class, "default");
		  }
		  this.studentNumber = studentNumber;
	  }
	  
	  public void setGroup(Group group) {
		  if( group != null ) {
			  this.theGroup = Key.create(group);  // Creating the Ancestor key
		  } else {
		      this.theGroup = Key.create(Group.class, "default");
		  }
	  }
	  
	  public Key<Group> getGroup() {
		  return theGroup;
	  }
	  
	  public Long getStudentNumber() {
		  return this.studentNumber;
	  }
	  
	  public int getPersonalQRCode() {
		  return this.personalQRCode;
	  }
	  
	  public void setPersonalQRCode(int code) {
		  this.personalQRCode = code;
		  this.qrCodeTimeStamp = new Date();
	  }
	  
	  public Date getQrCodeTimeStamp() {
		  return this.qrCodeTimeStamp;
	  }
}
