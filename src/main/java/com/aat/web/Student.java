package com.aat.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Student {
	  
	  @Parent private Key<Group> theGroup;
	  @Id public Long id;
	  private int studentNumber;

	  public Student() {
		  studentNumber = -1;
	  }
	  public Student(int group, int studentNumber) {
		  if( group != 0 ) {
			  this.theGroup = Key.create(Group.class, Integer.toString(group));  // Creating the Ancestor key
		  } else {
		      this.theGroup = Key.create(Group.class, "default");
		  }
		  this.studentNumber = studentNumber;
	  }
	  
	  public void setGroup(int group) {
		  if( group != 0 ) {
			  this.theGroup = Key.create(Group.class, Integer.toString(group));  // Creating the Ancestor key
		  } else {
		      this.theGroup = Key.create(Group.class, "default");
		  }
	  }
	  
	  public Key<Group> getGroup() {
		  return theGroup;
	  }
	  
	  public int getStudentNumber() {
		  return this.studentNumber;
	  }
}
