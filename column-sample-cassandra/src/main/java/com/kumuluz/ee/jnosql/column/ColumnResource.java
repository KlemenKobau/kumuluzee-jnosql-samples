package com.kumuluz.ee.jnosql.column;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("column")
@ApplicationScoped
public class ColumnResource {

	@Inject
	private ColumnBean columnBean;

//	@GET
//	@Path("{id}")
//	public Response getJohn(@PathParam("id") long id) {
//		Person john = columnBean.getPersonById(id);
//		return Response.ok(john).build();
//	}

	@GET
	public Response putJohn() {
		Oseba john = new Oseba();
		john.setIme("john");
		john.setId((long) 1);
		john.setPhones(List.of("phone1"));
		Oseba saved = columnBean.savePerson(john);
		return Response.ok(saved).build();
	}

	@GET
	@Path("count")
	public Response countEntities() {
		long count = columnBean.count();
		return Response.ok(count).build();
	}
}
