package com.aat.web;

import java.util.List;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.googlecode.objectify.ObjectifyService;

public class GroupResource extends ServerResource{

	@Get
	public String representGroups() {
		String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<grouplist>\n";
		List<Group> groupList = ObjectifyService.ofy().load().type(Group.class).list();
		for (Group gr: groupList) {
			result += "\t<group id="+gr.id+">\n";
			result += "\t\t<group_number>"+gr.getGroupNumber()+"</group_number>\n";
			result += "\t</group>\n";
		}
		result += "</grouplist>";
		return result;
	}
	
	@Post
	public String createGroup() {
		int groupNumber = Integer.parseInt(getAttribute("groupNumber"));
		//exist group?
		List<Group> groupList = ObjectifyService.ofy().load().type(Group.class).list();
		Group group = null;
		for (Group gr: groupList) {
			if (gr.getGroupNumber() == groupNumber) {
				group = gr;
			}
		}
		if(group!=null) {
			return "Group with group number'"+groupNumber+"' already exists.";
		}
		ObjectifyService.ofy().save().entity(new Group(groupNumber)).now();
		return "Group with group number '"+groupNumber+"' created.";
	}
	
	@Delete
	public String clearGroups() {
		List<Group> groupList = ObjectifyService.ofy().load().type(Group.class).list();
		ObjectifyService.ofy().delete().entities(groupList).now();
		return "Groups deleted";
	}
}
