# KumuluzEE JNoSQL column sample (Cassandra)
> Develop a basic REST service that uses Cassandra
> as its database

The objective of this sample is to use KumuluzEE JNoSQL
to connect to hazelcast and perform basic operations over it.
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

To run this sample you will need a running Hazelcast instance.
You can run it by using the docker compose in the parent directory.

## Usage
The example uses maven to build and run the microservice.

1. Build the sample using maven:

    ```bash
    $ cd kumuluzee-jnosql-samples/keyvalue-sample-hazelcast
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
 * JAX-RS REST resource, operations over Neo4J - http://localhost:8080/v1/key-value
 
To shut down the example simply stop the processes in the foreground.

## Tutorial

This tutorial will guide you through the steps required to create a service, which uses the KumuluzEE JNoSQL extension.
We will develop a simple REST service with the following resources:
* GET http://localhost:8080/v1/key-value/{id} - Getting a person by id
* GET http://localhost:8080/v1/key-value - Putting the initial person in the database

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

Add the `kumuluzee-jnosql-key-value`, `hazelcast-driver`, `kumuluzee-core`, `kumuluzee-servlet-jetty`, `kumuluzee-jax-rs-jersey`
 `kumuluzee-cdi-weld`, `kumuluzee-json-p-jsonp` and `kumuluzee-json-b-yasson` dependencies:
```xml
<dependencies>
    <dependency>
        <groupId>com.kumuluz.ee.jnosql</groupId>
        <artifactId>kumuluzee-jnosql-key-value</artifactId>
        <version>${kumuluzee-jnosql-key-value.version}</version>
    </dependency>
    <dependency>
        <groupId>org.jnosql.diana</groupId>
        <artifactId>hazelcast-driver</artifactId>
        <version>${diana.version}</version>
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

Unfortunately, JNoSQL still doesn't provide sufficient documentation for the configuration, but we can read it from the
HazelcastKeyValueConfiguration class, provided by the dependencies.

We can see that the provided fields are: 
* hazelcast.instanceName: the instance name
* hazelcast.host: as prefix to n host where n is the number of host, eg: hazelcast-host-1: host

The configuration we will be using will be:
```yaml
kumuluzee:
  name: kumuluzee-jnosql-key-value
  version: 1.0.0
  env:
    name: dev
  server:
    http:
      port: 8080
  jnosql:
    key-value:
      config-class-name: org.jnosql.diana.hazelcast.key.HazelcastKeyValueConfiguration
```

The **config-class-name** configuration is used to specify the configuration class of the database.

### Implement the servlet


Register your module as JAX-RS service and define the application path. You could do that in web.xml or
for example with `@ApplicationPath` annotation:

```java
@ApplicationPath("v1")
public class KeyValueApplication extends Application {
}
```

Implement JAX-RS resource, which we will use as the REST endpoint:

```java
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("key-value")
@ApplicationScoped
public class KeyValueResource {

	@Inject
	private KeyValueBean keyValueBean;

	@GET
	@Path("{id}")
	public Response getJohn(@PathParam("id") long id) {
		Person john = keyValueBean.getPersonById(id);
		return Response.ok(john).build();
	}

	@GET
	public Response putJohn() {
		Person john = new Person();
		john.setName("john");
		john.setId(1L);
		Person saved = keyValueBean.savePerson(john);
		return Response.ok(saved).build();
	}
}
```

We implement the bean with the database logic.

```java
@ApplicationScoped
public class KeyValueBean {

	@Inject
	private KeyValueTemplate keyValue;

	public Person getPersonById(long id) {
		Optional<Person> entity = keyValue.get(id, Person.class);
		return entity.orElse(null);
	}

	public Person savePerson(Person person) {
		return keyValue.put(person);
	}
}
```
We inject a template class, which offers more basic operations, including using the query language.

### Build the microservice and run it

To build the microservice and run the example, use the commands as described in previous sections.