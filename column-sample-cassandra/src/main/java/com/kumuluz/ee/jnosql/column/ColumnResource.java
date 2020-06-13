package com.kumuluz.ee.jnosql.column;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("column")
@ApplicationScoped
public class ColumnResource {

	@Inject
	private ColumnBean columnBean;

	@GET
	@Path("{id}")
	public Response getById(@PathParam("id") Long id) {
		Optional<Person> personOptional = columnBean.findById(id);

		if (personOptional.isPresent()) {
			return Response.ok(personOptional.get()).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@DELETE
	@Path("{id}")
	public Response deleteById(@PathParam("id") Long id) {
		columnBean.deleteById(id);
		return Response.noContent().build();
	}

	@GET
	public Response putInitial() {
		Person john = new Person();
		john.setId(1L);
		john.setName("john");

		john.setPhones(List.of("Phone 1", "Phone 2"));

		Person bob = new Person();
		bob.setId(2L);
		bob.setName("bob");
		bob.setPhones(List.of("")); // must not be an empty list

		List<Person> personList = new LinkedList<>();
		personList.add(john);
		personList.add(bob);
		columnBean.addPeople(personList);

		return Response.ok().build();
	}
}
