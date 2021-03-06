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
	public Response getJohn(@PathParam("id") long id) {
		Person john = keyValueBean.getPersonById(id);
		return Response.ok(john).build();
	}

	@GET
	public Response putJohn() {
		Person john = new Person();
		john.setName("john");
		john.setId(1L);
		Person saved = keyValueBean.savePerson(john);
		return Response.ok(saved).build();
	}
}
