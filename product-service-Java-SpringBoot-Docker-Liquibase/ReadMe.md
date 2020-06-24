# Story description

A basic Dockerized Springboot Maven application, build a single REST API endpoint that returns a filtered set of products from the provided data in the data.csv file

GET /product
Query Parameter			Description
type					The product type. (String. Can be 'phone' or 'subscription')
min_price				The minimum price in SEK. (Number)
max_price				The maximum price in SEK. (Number)
city					The city in which a store is located. (String)
property				The name of the property. (String. Can be 'color' or 'gb_limit')
property:color			The color of the phone. (String)
property:gb_limit_min 	The minimum GB limit of the subscription. (Number)
property:gb_limit_max 	The maximum GB limit of the subscription. (Number)

The expected response is a JSON array with the products in a 'data' wrapper. 

data": [
        {
            "type": "subscription",
            "properties": "gb_limit:10",
            "price": 202,
            "store_address": "Fausto vägen, Karlskrona"
        },

Solution should correctly filter any combination of API parameters and use some kind of a datastore.
All parameters are optional, all minimum and maximum fields should be inclusive (e.g. min_price=100&max_price=1000 should return items with price 100, 200... or 1000 SEK). 
The applications does not need to support multiple values (e.g. type=phone,subscription or property.color=green,red).
--------------------------------------------------------------------------------------------------------------------------------------------------------------------
# Technology stack used

Java 11
H2 database
Maven 3
Docker Engine (used 19.03.8) 
Springboot 2.3.0  for Microservices
Swagger API for Documentation
Liquibase is used for creating database schemas and data migration
Junit and Mockito for unit testing
Postman for API testing
-------------------------------------------------------------------------------------------------------------------------------------------------------------------
Major Design approaches 

JPA Specification:

Since we have many number of input query combinations , Implemented  JPA Specification to support run time JPA queries based on inputs given.
So we don't need to write each and every methods in repository layer. 
JPA Specifications supports flexible query generation mechanism to handle different types of input combinations.

Liquibase:

For the database side, implemented Liquibase to create and load data directly from data.csv file. 
Since Manual efforts for inserting large data into tables, I have used Liquibase  <loaddata> feature to loading CSV data directly .
Re-structred  data.csv file to support below code. So we could avoid 100's of lines code for INSERT statements.

<changeSet id="product-table-insert" author="telnor">
		<loadData encoding="UTF-8" file="db/data/data.csv"
			separator="," tableName="product" />
</changeSet>

File path : /product-service/src/main/resources/db/changelog/db.changelog-1.0.xml
-------------------------------------------------------------------------------------------------------------------------------------------------------------------
# Build steps and execution

1. Unzip the product-service.zip

2. navigate to the project folder

3. mvn clean install
4. Verify the logs and ensure that all the test cases executed successfully

# Docker build, and run commands
5. docker image build . -t api/product-service:1.0.1
6. docker images (optional command ) 
7. docker run -p 8080:8080 api/product-service:1.0.1

8.  check if application is running with swagger UI, http://localhost:8080/swagger-ui.html#/product-controller/getProductsUsingGET

# if 8080 port is already occupied , use commands " lsof -i :8080 "  and " kill -9 " to kill the process
# if we using the different port , Please update the same in postMan also.

Import file product-service-api-collection.postman_collection.json file into postman tool for API testing

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Enabled actuator for health Check with URL : http://localhost:8080/actuator/health

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

Following suggestions we can integrate for Production ready applications. 
This suggestions may be varied on project to project, but this is just came into my mind, 
because we have integrated the same in most of our previous projects

1. Spring Security : Spring security with oAuth2 ( token based authentication and authorization)
2. Kubernetes: Kubernetes for services orchestration (we can leverage public cloud platforms support)
3. DevOps :CI/CD for complete automation ( Jenkins,GoCD, or cloud platforms devOps tools)
4. Test Automation: Integrate Selenium and Cucumber for BDD
5. Spring Cloud : Integrate spring cloud for centralized application properties.
6. Spring Slueth and zipKin : Integrate it for distributed tracing 
7. If number of services are more, we can use Service Registry and API gateways for microservices
8. Apart from WEB dependency we have options like Spring WebFlux or Spring Websocket for implementing the same useCase
9. Use any monitoring tool for post-production monitoring ( DataDog, ELK etc)
10. Use message properties file instead of hard coding string messages in application code. So it helps internationalization also.











 



