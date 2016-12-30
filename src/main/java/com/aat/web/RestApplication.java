package com.aat.web;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class RestApplication extends Application{
	
	@Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());

        // Defines different routes
        router.attach("/signup/{studentNumber}/{groupNumber}", StudentResource.class);
        router.attach("/students", StudentResource.class);
        router.attach("/group/{groupNumber}", GroupResource.class);
        router.attach("/groups", GroupResource.class);
        router.attach("/qrcode/{studentNumber}", QRCodeResource.class);

        return router;
    }

}
