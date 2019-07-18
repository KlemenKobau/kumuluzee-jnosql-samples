package com.kumuluz.ee.jnosql.samples.graph;

import org.jnosql.artemis.Repository;

import java.util.Optional;

public interface NodeRepository extends Repository<Node, Long> {
	Optional<Node> findByName(String name);
}
