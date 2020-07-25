# KumuluzEE JNoSQL column sample (Cassandra)
> Develop a basic REST service that uses Cassandra
> as its database

The objective of this sample is to use KumuluzEE JNoSQL
to connect to Neo4J and perform basic operations over it.
The tutorial will guide you through all the necessary steps.
You will add KumuluzEE dependencies into pom.xml. 
You will prepare the configuration for connecting with the database
and create classes, that will expose REST endpoints and use them to 
perform operations over the database.

## Requirements

In order to run this example you will need the following:

1. Java 8 (or newer), you can use any implementation:
    * If you have installed Java, you can check the version by typing the following in a command line:
        
        ```
        java -version
        ```

2. Maven 3.2.1 (or newer):
    * If you have installed Maven, you can check the version by typing the following in a command line:
        
        ```
        mvn -version
        ```
3. Git:
    * If you have instaled Git, you can check the version by typing the following in a command line:
    
        ```
        git --version
        ```
## Prerequisites

To run this sample you will need a running Neo4J instance.
You can run it by using the docker compose in the parent directory.

## Usage
The example uses maven to build and run the microservice.

1. Build the sample using maven:

    ```bash
    $ cd kumuluzee-jnosql-samples/graph-sample-neo4j-remote
    $ mvn clean package
   
3. Run the sample:
* Uber-jar:

   ```bash
   $ java -jar target/${project.build.finalName}.jar
   ```
   
   in Windows environemnt use the command
   ```batch
   java -jar target/${project.build.finalName}.jar
   ```

* Exploded:

   ```bash
   $ java -cp target/classes:target/dependency/* com.kumuluz.ee.EeApplication
   ```
   
   in Windows environment use the command
   ```batch
   java -cp target/classes;target/dependency/* com.kumuluz.ee.EeApplication
  
 The application/service can be accessed on the following URL:
 * JAX-RS REST resource, operations over MongoDB - http://localhost:8080/v1/graph
 
To shut down the example simply stop the processes in the foreground.

## Tutorial

This tutorial will guide you through the steps required to create a service, which uses the KumuluzEE JNoSQL extension.
We will develop a simple REST service with the following resources:
* GET http://localhost:8080/v1/graph - Getting all nodes from the database
* GET http://localhost:8080/v1/graph/count - Getting the number of nodes in the database
* GET http://localhost:8080/v1/graph/insert-test - inserting a test node

We will follow these steps:
* Create a Maven project in the IDE of your choice (Eclipse, IntelliJ, etc.)
* Add Maven dependencies to KumuluzEE and include KumuluzEE components (Core, Servlet, JAX-RS)
* Add Maven dependency to the KumuluzEE JNoSQL extension
* Implement the service
* Build the microservice
* Run it

### Add Maven dependencies

Add the KumuluzEE BOM module dependency to your `pom.xml` file:
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.kumuluz.ee</groupId>
            <artifactId>kumuluzee-bom</artifactId>
            <version>${kumuluz.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Add the `kumuluzee-jnosql-graph`, `gremlin-core`, `gremlin-groovy`, `neo4j-gremlin-bolt`, `kumuluzee-core`, `kumuluzee-servlet-jetty`, `kumuluzee-jax-rs-jersey`
 `kumuluzee-cdi-weld`, `kumuluzee-json-p-jsonp` and `kumuluzee-json-b-yasson` dependencies:
```xml
<dependencies>
    <dependency>
        <groupId>com.kumuluz.ee.jnosql</groupId>
        <artifactId>kumuluzee-jnosql-graph</artifactId>
    </dependency>
    <dependency>
        <groupId>org.apache.tinkerpop</groupId>
        <artifactId>gremlin-core</artifactId>
        <version>${tinkerpop.version}</version>
    </dependency>
    <dependency>
        <groupId>org.apache.tinkerpop</groupId>
        <artifactId>gremlin-groovy</artifactId>
        <version>${tinkerpop.version}</version>
    </dependency>
    <dependency>
        <groupId>com.steelbridgelabs.oss</groupId>
        <artifactId>neo4j-gremlin-bolt</artifactId>
        <version>${neo4j-gremlin-bolt.version}</version>
    </dependency>
    <dependency>
        <groupId>com.kumuluz.ee</groupId>
        <artifactId>kumuluzee-core</artifactId>
    </dependency>
    <dependency>
        <groupId>com.kumuluz.ee</groupId>
        <artifactId>kumuluzee-servlet-jetty</artifactId>
    </dependency>
    <dependency>
        <groupId>com.kumuluz.ee</groupId>
        <artifactId>kumuluzee-jax-rs-jersey</artifactId>
    </dependency>
    <dependency>
        <groupId>com.kumuluz.ee</groupId>
        <artifactId>kumuluzee-cdi-weld</artifactId>
    </dependency>
    <dependency>
        <groupId>com.kumuluz.ee</groupId>
        <artifactId>kumuluzee-json-p-jsonp</artifactId>
    </dependency>
    <dependency>
        <groupId>com.kumuluz.ee</groupId>
        <artifactId>kumuluzee-json-b-yasson</artifactId>
    </dependency>
</dependencies>
```

Add the `kumuluzee-maven-plugin` build plugin to package microservice as uber-jar:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.kumuluz.ee</groupId>
            <artifactId>kumuluzee-maven-plugin</artifactId>
            <version>${kumuluzee.version}</version>
            <executions>
                <execution>
                    <id>package</id>
                    <goals>
                        <goal>repackage</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

or exploded:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.kumuluz.ee</groupId>
            <artifactId>kumuluzee-maven-plugin</artifactId>
            <version>${kumuluzee.version}</version>
            <executions>
                <execution>
                    <id>package</id>
                    <goals>
                        <goal>copy-dependencies</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

### Write configuration
We have to configure the database connection, by adding the configuration inside **config.yaml**, which is used
for all KumuluzEE configurations.

Unfortunately, JNoSQL still doesn't provide sufficient documentation for the configuration.

The required fields for Neo4J are not specified by JNOSQL, but these seem to be the only ones needed.
* neo4j-graph-name
* neo4j-identifier
* neo4j-username
* neo4j-password
* neo4j-url
* neo4j-readonly
* neo4j-vertexIdProvider
* neo4j-edgeIdProvider
* neo4j-propertyIdProvider

The configuration we will be using will be:
```yaml
kumuluzee:
  name: kumuluzee-jnosql-graph
  version: 1.0.0
  env:
    name: dev
  server:
    http:
      port: 8080
  jnosql:
    graph:
      gremlin-graph: com.steelbridgelabs.oss.neo4j.structure.Neo4JGraph

      neo4j-graph-name: "test"
      neo4j-identifier: "identifier"
      neo4j-username: "neo4j"
      neo4j-password: "1234"
      neo4j-url: bolt://localhost:7687
      neo4j-readonly: false
      neo4j-vertexIdProvider: com.steelbridgelabs.oss.neo4j.structure.providers.Neo4JNativeElementIdProvider
      neo4j-edgeIdProvider: com.steelbridgelabs.oss.neo4j.structure.providers.Neo4JNativeElementIdProvider
      neo4j-propertyIdProvider: com.steelbridgelabs.oss.neo4j.structure.providers.Neo4JNativeElementIdProvider
```

The **gremlin.graph** configuration is used to specify the configuration class of the database.

### Implement the servlet


Register your module as JAX-RS service and define the application path. You could do that in web.xml or
for example with `@ApplicationPath` annotation:

```java
@ApplicationPath("v1")
public class GraphApplication extends Application {
}
```

Implement JAX-RS resource, which we will use as the REST endpoint:

```java
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
```

We implement the bean with the database logic.

```java
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
```

Here we inject a repository with basic operations.
These operations can be extended by defining functions based on the specifications from the JNoSQL documentation.
We also inject a template class, which offers more basic operations, including using the query language.

We also extend the repository with a function, annotated with the graph query language.
```java
public interface NodeRepository extends Repository<Node, Long> {

	@Query("g.V('0');")
	List<Node> getAllNodes();
}
```
### Build the microservice and run it

To build the microservice and run the example, use the commands as described in previous sections.