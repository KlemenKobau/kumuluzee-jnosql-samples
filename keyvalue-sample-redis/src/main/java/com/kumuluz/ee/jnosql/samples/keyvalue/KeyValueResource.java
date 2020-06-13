package com.kumuluz.ee.jnosql.samples.keyvalue;

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
@Path("key-value")
@ApplicationScoped
public class KeyValueResource {

	@Inject
	private KeyValueBean keyValueBean;

	@GET
	@Path("{id}")
	public Response getById(@PathParam("id") Long id) {
		Optional<Person> personOptional = keyValueBean.findById(id);

		if (personOptional.isPresent()) {
			return Response.ok(personOptional.get()).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@DELETE
	@Path("{id}")
	public Response deleteById(@PathParam("id") Long id) {
		keyValueBean.deleteById(id);
		return Response.noContent().build();
	}

	@GET
	public Response putInitial() {
		Person john = new Person();
		john.setId(1L);
		john.setName("john");

		Person bob = new Person();
		bob.setId(2L);
		bob.setName("bob");

		List<Person> seznamLjudi = new LinkedList<>();
		seznamLjudi.add(john);
		seznamLjudi.add(bob);
		keyValueBean.addPeople(seznamLjudi);

		return Response.ok().build();
	}
}
