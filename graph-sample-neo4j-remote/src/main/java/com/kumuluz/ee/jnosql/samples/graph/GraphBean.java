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
package com.kumuluz.ee.jnosql.samples.graph;

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;
import org.jnosql.artemis.graph.GraphTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Klemen Kobau
 */
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
