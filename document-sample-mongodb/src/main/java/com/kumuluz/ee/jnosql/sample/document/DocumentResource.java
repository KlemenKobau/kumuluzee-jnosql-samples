package com.kumuluz.ee.jnosql.sample.document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("document")
@ApplicationScoped
public class DocumentResource {

	@Inject
	private DocumentBean documentBean;

	@GET
	@Path("{id}")
	public Response getJohn(@PathParam("id") long id) {
		Person john = documentBean.getPersonById(id);
		return Response.ok(john).build();
	}

	@GET
	public Response putJohn() {
		Person john = new Person();
		john.setName("john");
		john.setId(1L);
		john.setPhones(List.of("phone1"));
		Person saved = documentBean.savePerson(john);
		return Response.ok(saved).build();
	}
}
