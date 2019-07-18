package com.kumuluz.ee.jnosql.samples.graph;

import org.apache.tinkerpop.gremlin.structure.Graph;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class GraphProducer {

	private static final String FILE_CONF = "conf/janusgraph-berkeleyje-lucene.properties";

	private Graph graph;

	@PostConstruct
	public void init() {

	}

	@Produces
	@ApplicationScoped
	public Graph getGraph() {
		return graph;
	}

	public void close(@Disposes Graph graph) throws Exception {
		graph.close();
	}
}
