package com.aat.web;

import java.util.List;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class StudentResource extends ServerResource {
	
	@Get
	public String representStudents() {
		String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<studentlist>\n";
		List<Student> studentList = ObjectifyService.ofy().load().type(Student.class).list();
		for (Student st: studentList) {
			Key<Student> key = Key.create(Student.class, Integer.toString(st.getStudentNumber()));
			List<QRCode> qrCodes = ObjectifyService.ofy().load().type(QRCode.class).ancestor(key).list();
			List<Participation> participations = ObjectifyService.ofy().load().type(Participation.class).ancestor(key).list();
			result += "\t<student id="+st.id+">\n";
			result += "\t\t<student_number>"+st.getStudentNumber()+"</student_number>\n";
			result += "\t\t<group>"+st.getGroup().getName()+"</group>\n";
			result += "\t\t<number_participations>"+participations.size()+"<number_participations>\n";
			result += "\t\t<number_qr_codes>"+qrCodes.size()+"<number_qr_codes>\n";
			result += "\t</student>\n";
		}
		result += "</studentlist>";
		return result;
	}
	
	@Put
	public String signUpStudent() {
		int studentNumber = Integer.parseInt(getAttribute("studentNumber"));
		int groupNumber = Integer.parseInt(getAttribute("groupNumber"));
		if(groupNumber==0 || studentNumber == 0) {
			return "Group number '"+groupNumber+"' or Student '"+studentNumber+"' number is 0"; 
		}
		
		//exist group?
		List<Group> groupList = ObjectifyService.ofy().load().type(Group.class).list();
		Group group = null;
		for (Group gr: groupList) {
			if (gr.getGroupNumber() == groupNumber) {
				group = gr;
			}
		}
		if(group==null) {
			return "Group with group number'"+groupNumber+"' does not exist.";
		}
		
		//Student already created?
		List<Student> studentList = ObjectifyService.ofy().load().type(Student.class).list();
		Student student = null;
		for (Student st: studentList) {
			if (st.getStudentNumber() == studentNumber) {
				student = st;
			}
		}
		if(student == null) {
			student = new Student(groupNumber, studentNumber);
			//save new Student
			ObjectifyService.ofy().save().entity(student).now();
			return "Student with student number '"+studentNumber+"' was created and placed in group '"+groupNumber+"'";
		} else {
			//place student in the right group
			Key<Group> oldKey = student.getGroup();
			student.setGroup(groupNumber);
			return "Student was moved from group '"+oldKey.getName()+"' to group '"+groupNumber+"'";
		}
	}
	
	@Delete
	public String clearStudents() {
		List<Student> studentList = ObjectifyService.ofy().load().type(Student.class).list();
		ObjectifyService.ofy().delete().entities(studentList).now();
		return "Students deleted";
	}
	
}
