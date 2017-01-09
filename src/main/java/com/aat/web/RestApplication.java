package com.aat.web;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class RestApplication extends Application{
	
	@Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());

        // Defines different routes
        //GET
        router.attach("/groups", GroupResource.class);			//returns list of existing groups
        router.attach("/students", StudentResource.class); 		//returns list of existing students
        router.attach("/participation/{studentNumber}", ParticipationResource.class);	//returns overview of participations for given studentNumber
        router.attach("/qrcodes", QRCodeCreationResource.class);	//returns list of existing qrcodes
        //POST
        router.attach("/group/{groupNumber}", GroupResource.class);		//creates a group with given groupNumber
        router.attach("/qrcode/new/{studentNumber}", QRCodeCreationResource.class);		//creates a new qrCode for the given student and returns a randNumber with a timestamp (both need to be given for validation)
        //PUT
        router.attach("/signup/{studentNumber}/{groupNumber}", StudentResource.class);	//creates a student with given studentNumber and puts him in the group (groupNumber must exist)
        router.attach("/qrcode/valid/{studentNumber}/{time}/{randNumber}", QRCodeValidationResource.class);	//validates the given qrcode (time and randNumber) for the studentNumber
        //DELETE
        router.attach("/clear/students", StudentResource.class);		//deletes all students
        router.attach("/clear/groups", GroupResource.class);			//deletes all groups
        router.attach("/clear/qrcodes", ParticipationResource.class);	//deletes all qrCodes

        return router;
    }

}
