/*
 *  Copyright (c) 2014-2017 Kumuluz and/or its affiliates
 *  and other contributors as indicated by the @author tags and
 *  the contributor list.
 *
 *  Licensed under the MIT License (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  https://opensource.org/licenses/MIT
 *
 *  The software is provided "AS IS", WITHOUT WARRANTY OF ANY KIND, express or
 *  implied, including but not limited to the warranties of merchantability,
 *  fitness for a particular purpose and noninfringement. in no event shall the
 *  authors or copyright holders be liable for any claim, damages or other
 *  liability, whether in an action of contract, tort or otherwise, arising from,
 *  out of or in connection with the software or the use or other dealings in the
 *  software. See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.kumuluz.ee.jnosql.samples.keyvalue;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Klemen Kobau
 */
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

		List<Person> personList = new LinkedList<>();
		personList.add(john);
		personList.add(bob);
		keyValueBean.addPeople(personList);

		return Response.ok().build();
	}
}
