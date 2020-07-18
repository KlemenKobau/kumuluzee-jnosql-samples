# KumuluzEE JNoSQL column sample (Cassandra)
> Develop a basic REST service that uses Cassandra
> as its database

The objective of this sample is to use KumuluzEE JNoSQL
to connect to Cassandra and perform basic operations over it.
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

To run this sample you will need a running Cassandra instance.
You can run it by using the docker compose in the parent directory.

## Usage
The example uses maven to build and run the microservice.

1. Build the sample using maven:

    ```bash
    $ cd kumuluzee-jnosql-samples/column-sample-cassandra
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
 * JAX-RS REST resource, operations over Cassandra - http://localhost:8080/v1/column
 
To shut down the example simply stop the processes in the foreground.