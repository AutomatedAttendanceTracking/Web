package com.aat.web;

import java.util.Date;
import java.util.List;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class ParticipationResource extends ServerResource {
	
	@Get
	public String representParticipations() {
		int studentNumber = Integer.parseInt(getAttribute("studentNumber"));
		List<Student> studentList = ObjectifyService.ofy().load().type(Student.class).list();
		Student student = null;
		for (Student st: studentList) {
			if(st.getStudentNumber() == studentNumber) {
				student = st;
			}
		}
		if(student== null) {
			return "Student number does not exist.";
		}
		String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<participations>\n";
		Key<Student> key = Key.create(Student.class, Integer.toString(student.getStudentNumber()));
		List<Participation> participations = ObjectifyService.ofy().load().type(Participation.class).ancestor(key).list();
		for (Participation p: participations) {
			result += "\t<exercise>\n";
			result += "\t\t<date_milliseconds>"+p.getDate().getTime()+"</data_milliseconds>\n";
			result += "\t\t<date_string>"+p.getDate().toString()+"</date_string>\n";
			result += "\t</exercise>\n";
		}
		result += "</participations>";
		return result;
	}
	
	@Delete
	public String deleteQrCodesParticipations() {
		List<QRCode> qrCodes = ObjectifyService.ofy().load().type(QRCode.class).list();
		ObjectifyService.ofy().delete().entities(qrCodes).now();
		List<Participation> parti = ObjectifyService.ofy().load().type(Participation.class).list();
		ObjectifyService.ofy().delete().entities(parti).now();
		return "QRCodes/Participations deleted";
	}
}
