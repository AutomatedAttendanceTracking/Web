package com.aat.web;

import java.util.Date;
import java.util.List;

import org.restlet.Response;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class QRCodeValidationResource extends ServerResource{
	public static final int VALIDATIONTIME = 3600000;

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
		QRCode newQrCode = new QRCode(student.getStudentNumber(), new Date(milliseconds), code);
		Key<Student> key = Key.create(Student.class, Integer.toString(student.getStudentNumber()));
		List<QRCode> qrCodes = ObjectifyService.ofy().load().type(QRCode.class).ancestor(key).list();
		for (QRCode q: qrCodes) {
			if (newQrCode.getTimestamp().getTime()-q.getTimestamp().getTime() <= VALIDATIONTIME && q.getRandValue() == newQrCode.getRandValue()) {
				Participation participation = new Participation(student.getStudentNumber(), newQrCode.getTimestamp());
				ObjectifyService.ofy().save().entity(participation).now();
				deleteOldQrCodes(qrCodes, q);
				return "Participation saved.";
			}
		}
		return "Saving particiaption failed.";
	}
	
	private void deleteOldQrCodes(List<QRCode> qrCodes, QRCode qrCode) {
		int index = qrCodes.indexOf(qrCode);
		System.out.println("Size: "+qrCodes.size());
		System.out.println("Index: "+index);
		ObjectifyService.ofy().delete().entities(qrCodes.subList(0, index+1)).now();
	}
}
