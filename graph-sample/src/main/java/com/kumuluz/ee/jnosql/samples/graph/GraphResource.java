package com.kumuluz.ee.jnosql.samples.graph;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("graph")
public class GraphResource {
	@GET
	public Response getAllNodes() {
		return null;
	}
}
