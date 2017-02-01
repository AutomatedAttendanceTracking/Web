package com.aat.web;

import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class BonusResource extends ServerResource {
	  private static final int MAXMISSING = 2;
	  private static final int NUMBERTOTALEXERCISE = 4;
	
	@Get
	public String computeBonus() {
		int studentNumber = Integer.parseInt(getAttribute("studentNumber"));
		List<Student> studentList = ObjectifyService.ofy().load().type(Student.class).list();
		Student student = null;
		for (Student st: studentList) {
			if (st.getStudentNumber() == studentNumber) {
				student = st;
			}
		}
		if(student == null) {
			return "Could not find student with student number '"+studentNumber+"'";
		}
		Key<Student> key = Key.create(Student.class, Integer.toString(student.getStudentNumber()));
		List<Participation> participations = ObjectifyService.ofy().load().type(Participation.class).ancestor(key).list();
		if(participations.size() >= NUMBERTOTALEXERCISE-MAXMISSING) {
			return "True";
		} else {
			return "False";
		}
	}

}
