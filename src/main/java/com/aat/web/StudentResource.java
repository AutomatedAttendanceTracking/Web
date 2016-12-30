package com.aat.web;

import java.util.List;

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
			result += "\t<student id="+st.id+">\n";
			result += "\t\t<student_number>"+st.getStudentNumber()+"</student_number>\n";
			result += "\t\t<group>"+st.getGroup().getName()+"</group>\n";
			result += "\t</student>\n";
		}
		result += "</studentlist>";
		return result;
	}
	
	@Put
	public String signUpStudent() {
		Long studentNumber = Long.parseLong(getAttribute("studentNumber"));
		Long groupNumber = Long.parseLong(getAttribute("groupNumber"));
		if(groupNumber==null || studentNumber == null) {
			return "Group number '"+groupNumber+"' or Student '"+studentNumber+"' number is null"; 
		}
		
		//exist group?
		List<Group> groupList = ObjectifyService.ofy().load().type(Group.class).list();
		Group group = null;
		for (Group gr: groupList) {
			if (gr.getGroupNumber().equals(groupNumber)) {
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
			if (st.getStudentNumber().equals(studentNumber)) {
				student = st;
			}
		}
		if(student == null) {
			student = new Student(group, studentNumber);
			//save new Student
			ObjectifyService.ofy().save().entity(student).now();
			return "Student with student number '"+studentNumber+"' was created and placed in group '"+groupNumber+"'";
		} else {
			//place student in the right group
			Key<Group> oldKey = student.getGroup();
			student.setGroup(group);
			return "Student was moved from group '"+oldKey.getName()+"' to group '"+groupNumber+"'";
		}
	}
	
}
