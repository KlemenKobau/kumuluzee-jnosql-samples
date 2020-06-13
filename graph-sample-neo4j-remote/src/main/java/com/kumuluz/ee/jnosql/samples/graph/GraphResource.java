package com.kumuluz.ee.jnosql.samples.graph;

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
@Path("graph")
@ApplicationScoped
public class GraphResource {

	@Inject
	private GraphBean graphBean;

	@GET
	public Response getAllNodes() {
		List<Node> allNodes = graphBean.getAll();
		return Response.ok(allNodes).build();
	}

	@GET
	@Path("count")
	public Response getNodeCount() {
		return Response.ok(graphBean.count()).build();
	}

	@GET
	@Path("insert-test")
	public Response insertNode() {
		Node node = new Node();
		node.setName("test");
		Node res = graphBean.insert(node);
		return Response.ok(res).build();
	}
}
