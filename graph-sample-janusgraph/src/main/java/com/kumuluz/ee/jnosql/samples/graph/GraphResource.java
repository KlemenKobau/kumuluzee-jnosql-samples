package com.kumuluz.ee.jnosql.samples.graph;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("graph")
@ApplicationScoped
public class GraphResource {

	@Inject
	private GraphBean graphBean;

	@GET
	public Response getAllNodes() {
		Node node = new Node();
		node.setName("test");
		Node res = graphBean.insert(node);
		Long count = graphBean.getAll();
		System.err.println(count);
		return Response.ok().build();
	}
}
