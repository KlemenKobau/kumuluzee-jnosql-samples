package com.kumuluz.ee.jnosql.samples.graph;

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;
import org.jnosql.artemis.graph.GraphTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class GraphBean {

	@Inject
	private GraphTemplate template;

	@Inject
	private Graph graph;

	@Inject
	@Database(DatabaseType.GRAPH)
	private NodeRepository nodeRepository;

	public Node insert(Node node) {
		Node res = template.insert(node);
		graph.tx().commit();
		return res;
	}

	public List<Node> getAll() {
		return nodeRepository.getAllNodes();
	}

	public long count() {
		return nodeRepository.count();
	}
}
