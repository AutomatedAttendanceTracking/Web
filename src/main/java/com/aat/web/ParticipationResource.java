package com.aat.web;

import java.util.Date;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.googlecode.objectify.ObjectifyService;

public class ParticipationResource extends ServerResource {
	
	@Get
	public String representParticipations() {
		Long studentNumber = Long.parseLong(getAttribute("studentNumber"));
		List<Student> studentList = ObjectifyService.ofy().load().type(Student.class).list();
		Student student = null;
		for (Student st: studentList) {
			if(st.getStudentNumber().equals(studentNumber)) {
				student = st;
			}
		}
		if(student== null) {
			return "Student number does not exist.";
		}
		String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<participations>\n";
		for (Date d: student.getParticipations()) {
			result += "\t<exercise>\n";
			result += "\t\t<date_milliseconds>"+d.getTime()+"</data_milliseconds>\n";
			result += "\t\t<date_string>"+d.toString()+"</date_string>\n";
			result += "\t</exercise>\n";
		}
		result += "</participations>";
		return result;
	}
}
