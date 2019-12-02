
myRetail RESTful service

Technologies
------------

JDK 1.7 or 1.8

Mongo DB

Maven 4.0

SpringBoot

EhCache

Database Information
---------------------

database=myretail

mongodb host=localhost

mongodb port=27017

mongo collection name =productprice

use myretail

db.productprice.insert({ "_id" : 13860428, "price" : 13.49, "currencyCode" : "USD" })

db.productprice.insert({ "_id" : 50513417,"price" : 100.00, "currencyCode" : "USD" })

db.productprice.insert({ "_id" : 49102103,"price" : 299.99, "currencyCode" : "USD" })

Follow the the below steps to build and run an application.
-----------------------------------------------------------
import the project to eclipse as a maven project and build as maven project.

Run the MyRetialLoader to load the application on the server.

					OR

cd myRetail

Then run

mvn clean package

Then run the jar

java -jar target/myRetail-0.0.1-SNAPSHOT.jar

8082 is the port on which the application runs

Below are the endpoints available for the application (Sample request and response is provided.)
------------------------------------------------------------------------------------------------

1. GET: http://localhost:8082/product/13860428 : will return json object with product information and pricing information.

Response:-

{ "id": 13860428, "name": "Kill Bill", "productPrice": { "price": 1000, "currencyCode": "USD" } }

2. PUT: http://localhost:8082/product/13860428 send JSON object with updated price in request body, it will return JSON object with updated pricing information.

Request Body:-

{ "id": 13860428, "productPrice": { "price": 1000.00, "currencyCode": "USD" } }

Response :-

{ "id": 13860428, "name": "Kill Bill", "productPrice": { "price": 1000.00, "currencyCode": "USD" } }

3. POST: http://localhost:8082/product/13860429 send JSON object with new price in request body, it will return JSON object with added pricing information.

Request Body:-

{ "id": 13860429, "productPrice": { "price": 10000.00, "currencyCode": "INR" } }

Response :-

{ "id": 13860429, "name": "Kill Bill", "productPrice": { "price": 10000.00, "currencyCode": "INR" } }