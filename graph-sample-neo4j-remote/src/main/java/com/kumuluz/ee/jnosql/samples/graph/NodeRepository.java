package com.kumuluz.ee.jnosql.samples.graph;

import org.jnosql.artemis.Query;
import org.jnosql.artemis.Repository;

import java.util.List;

public interface NodeRepository extends Repository<Node, Long> {

	@Query("g.V('0');")
	public List<Node> getAllNodes();
}
