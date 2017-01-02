package com.aat.web;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.googlecode.objectify.ObjectifyService;

public class QRCodeCreationResource extends ServerResource{

	@Put
	public String createQRCode() {
		Long studentNumber = Long.parseLong(getAttribute("studentNumber"));
		int randValue;
		List<Student> studentList = ObjectifyService.ofy().load().type(Student.class).list();
		Student student = null;
		for(Student st: studentList) {
			if(st.getStudentNumber().equals(studentNumber)) {
				student = st;
			}
		}
		if(student == null) {
			return "Student with student number '"+studentNumber+"' could not be found.";
		}
		try {
			SecureRandom random = SecureRandom.getInstanceStrong();
			randValue = random.nextInt();
		} catch (NoSuchAlgorithmException e) {
			return "Creating random number failed."+e;
		}
		Pair<Date, Integer> qrCode = student.addPersonalQRCode(randValue);
		return "Creation successful. QrCode(<time in milliseconds>, <randNumber>): "+qrCode.getLeft().getTime()+","+qrCode.getRight();
	}
}