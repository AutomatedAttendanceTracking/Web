package com.aat.web;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.googlecode.objectify.ObjectifyService;

public class QRCodeCreationResource extends ServerResource{

	@Get
	public String representQRCodes() {
		String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<qrcodelist>\n";
		List<QRCode> qrCodes = ObjectifyService.ofy().load().type(QRCode.class).list();
		for (QRCode qr: qrCodes) {
			result += "\t<qrcode id="+qr.id+">\n";
			result += "\t\t<timestamp>"+qr.getTimestamp()+"</timestamp>\n";
			result += "\t\t<random_value>"+qr.getRandValue()+"</random_value>\n";
			result += "\t</qrcode>\n";
		}
		result += "</qrcodelist>";
		return result;
	}
	
	@Post
	public String createQRCode() {
		int studentNumber = Integer.parseInt(getAttribute("studentNumber"));
		int randValue;
		List<Student> studentList = ObjectifyService.ofy().load().type(Student.class).list();
		Student student = null;
		for(Student st: studentList) {
			if(st.getStudentNumber() == studentNumber) {
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
		QRCode qrCode = new QRCode(student.getStudentNumber(), new Date(), randValue);
		ObjectifyService.ofy().save().entity(qrCode).now();
		return "Creation successful. QrCode(<time in milliseconds>, <randNumber>): "+qrCode.getTimestamp().getTime()+","+qrCode.getRandValue();
	}
}
