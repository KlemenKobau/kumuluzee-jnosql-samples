package com.kumuluz.ee.jnosql.samples.graph;

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.jnosql.artemis.graph.GraphTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GraphBean {

	@Inject
	private GraphTemplate template;

	@Inject
	private Graph graph;

	public Node insert(Node node) {
		Node res = template.insert(node);
		graph.tx().commit();
		return res;
	}

	public Long getAll() {
		return template.count(Node.class);
	}

}
