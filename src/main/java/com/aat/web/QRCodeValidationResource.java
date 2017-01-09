package com.aat.web;

import java.util.Date;
import java.util.List;

import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.googlecode.objectify.ObjectifyService;

public class QRCodeValidationResource extends ServerResource{

	@Put
	public String saveParticipation() {
		int studentNumber = Integer.parseInt(getAttribute("studentNumber"));
		Long milliseconds = Long.parseLong(getAttribute("time"));
		int code = Integer.parseInt(getAttribute("randNumber"));
		
		//find student
		List<Student> studentList = ObjectifyService.ofy().load().type(Student.class).list();
		Student student = null;
		for (Student st: studentList) {
			if(st.getStudentNumber() == studentNumber) {
				student = st;
			}
		}
		if(student == null) {
			return "Student with student number '"+studentNumber+"' could not be found.";
		}
		Pair<Date, Integer> qrCode = new Pair<>(new Date(milliseconds), code);
		if(student.getQrCodes().contains(qrCode)) {
			student.addParticipation(qrCode.getLeft());
			student.deleteOldQrCodes(qrCode);
		}
		ObjectifyService.ofy().save().entity(student).now();
		return "Participation saved.";
	}
}
