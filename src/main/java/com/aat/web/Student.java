package com.aat.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Student {
	  private static final int MAXMISSING = 2;
	  
	  @Parent private Key<Group> theGroup;
	  @Id public Long id;
	  private Long studentNumber;
	  private List<Pair<Date, Integer>> qrCodes;
	  private boolean bonus;
	  private int numberTotalExercise;
	  private List<Date> participations;

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
	  
	  public List<Pair<Date,Integer>> getQrCodes() {
		  return this.qrCodes;
	  }
	  
	  public boolean getBonus() {
		  return this.bonus;
	  }
	  
	  public List<Date> getParticipations() {
		  return this.participations;
	  }
	  
	  public Pair<Date, Integer> addPersonalQRCode(int code) {
		  Pair<Date, Integer> qrCode = new Pair<>(new Date(), code);
		  this.qrCodes.add(new Pair<Date, Integer>(new Date(), code));
		  return qrCode;
	  }
	  
	  public void computeBonus() {
		  if(this.participations.size()>=this.numberTotalExercise-this.MAXMISSING) {
			  this.bonus = true;
		  } else {
			  this.bonus = false;
		  }
	  }
	  
	  public String addParticipation(Date timeStamp) {
		  Calendar calNow = Calendar.getInstance();
		  Calendar calLast = Calendar.getInstance();
		  calNow.setTime(timeStamp);
		  Date lastParticipation = this.participations.get(this.participations.size()-1);
		  calLast.setTime(lastParticipation);
		  if (calLast.before(calNow) && calLast.WEEK_OF_YEAR != calNow.WEEK_OF_YEAR) {
			  this.participations.add(calNow.getTime());
			  return "Participation saved.";
		  }
		  return "Student has already participated this week.";
	  }
	  
	  public void deleteOldQrCodes(Pair<Date, Integer> qrCode) {
		  int index = this.qrCodes.indexOf(qrCode);
		  for(int i=0; i<=index; i++) {
			  this.qrCodes.remove(i);
		  }
	  }
}
