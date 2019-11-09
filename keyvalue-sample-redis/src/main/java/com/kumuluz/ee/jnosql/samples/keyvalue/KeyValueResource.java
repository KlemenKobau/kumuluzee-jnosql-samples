package com.kumuluz.ee.jnosql.samples.keyvalue;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("key-value")
@ApplicationScoped
public class KeyValueResource {

	@Inject
	private KeyValueBean keyValueBean;

	@GET
	@Path("{id}")
	public Response getJohn(@PathParam("id") long id) {
		return Response.ok().build();
	}

	@GET
	public Response putJohn() {
		Person john = new Person();
		john.setName("john");
		john.setId((long) 1);
		return Response.ok().build();
	}
}
