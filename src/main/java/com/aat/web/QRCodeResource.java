package com.aat.web;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.googlecode.objectify.ObjectifyService;

public class QRCodeResource extends ServerResource{

	@Put
	public String createQRCode() {
		Long studentNumber = Long.parseLong(getAttribute("studentNumber"));
		int randValue;
		try {
			SecureRandom random = SecureRandom.getInstanceStrong();
			randValue = random.nextInt();
		} catch (NoSuchAlgorithmException e) {
			return "Creating random number failed."+e;
		}
		List<Student> studentList = ObjectifyService.ofy().load().type(Student.class).list();
		for(Student st: studentList) {
			if(st.getStudentNumber().equals(studentNumber)) {
				st.setPersonalQRCode(randValue);
			}
		}
		return "Creation successful. Random number:"+randValue;
	}
}
