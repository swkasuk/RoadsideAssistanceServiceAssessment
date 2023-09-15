##EmergencyRoadsideAssistance Serivce

## Description

- This project utilizes Spring Boot framework and Java, Java Persistence to build and implement the REST API Service that helps the customer with a disabled vehicle to get immediate roadside assistance.

  The EmergencyRoadsideAssistance service provides :
- The customer an ability to search and find the 5 nearest assistants, reserve and release an assistant. 
- The service provider assistant to update their location.
- Return the 5 nearest service trucks i.e assistants ordered by ascending distance
		-This is achieved by computing the geohash of the customer's location and comparing it with the assistants geohash based on their recent location.
- Reserve a service provider for a customer
- Release a service provider from a customer



## Contents

- [Preconditions](#preconditions)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Assumptions](#assumptions)
- [Database](#database)
- [Build and Execution](#build-and-execution)
- [Endpoints](#endpoints)
- [Testing](#testing)

## Preconditions

- Java Development Kit (JDK) installed (Java 8 or later)
- Maven build tool installed
- Integrated Development Environment (IDE) with Spring Boot support (e.g., STS, Eclipse ,IntelliJ IDEA)

## Getting Started

Clone this repository to your local machine:

## Project Structure
- src/main/java contains all the source files.
- com.assignment.ras.model - package contains all the dto objects
- com.assignment.ras.persistence - package contains all the database repositories and entity classes.
- com.assignment.ras.serivce - package contains the service interface and the implementation classes.
- com.assignment.ras.util -  package contains the utility classes to compute the GeoHash etc.
- src/main/resources folder contains the configuration file used to specify the configuration properites of this application.
- For the persistence store ,we are using the in memory H2 database and the initial data to be loaded on the application start up is from the file 'data.sql'.

## Assumptions
- Customers and ServiceProviders(Assistants) have access to the systems that can provide geolocation data to the RoadsideAssistance service as provided in the requirement document.
- The location data provided by the service providers is relatively up to date and accurate.
- For this assignment project , a sample data of the service providers including their geolocation data is added as a start up script. In real time this data will be managed by 
for eg: Add service provider usecase/flow. 
- This is also applicable to the Customer information which is persisted int he Customer table. Considering the time and scope of the assignment, the customer/Assistant tables are designed to have necessary fields , but in realtime these tables will have the customer/ assistant details like contact information etc.


## Database
- An in memory H2 database is used for this project. The database can be accessed via H2 console available at http://localhost:8312/roa/h2-console once the application is started.
- Currently the project is configured to have DB close on application exit. This means the DB will be dropped and recreated every time the spring boot application is run.
- A simplified representation of the database schema is as below.

- **ASSISTANT**
	
	- Assisitant_Id is the primary key and uniquely identifies the assistant
	- Reserved_ind indicates if the assistant is currently assigned to any customer or not
	- Active_ind indicates if the assistant is active and is available.
- **ASSISTANT_GEO_LOCATION** : The table to store the latest Geo location information of the assistant

	- Assistant_LOC_ID is the primary key
	- Assistant_id is the foreign key to the table Assistant
- **CUSTOMER** : This table is to store the customer information 
	
	- CUSTOMER_ID is the primary key and uniquely dentifies a customer.

- **SERVICE_REQUEST** : This table is to have a log of the service requests created when a customer is assigned with a service assistant.
	
	- SRVC_REQ_ID is the primary key and uniquely identifies a service request for a given customer and assistant.
	- SRVC_LOC_LAT,SRVC_LOC_LONG represents the geo location to which a service is requested.
	- SRVC_STATUS represents the status of the service request. (Initial status will be Assigned and can be updated to either Completed /Cancelled)

## Build and Execution

1. Build the project using Maven 
mvn clean install

2. Run the Springboot application
The application can either be run by selecting the project root in the Package explorer of the IDE and right clicka nd choose run as spring boot app or use the maven command 
mvn spring-boot:run

3. Once the application is started the service should be available and accessible at accessible at 'http://localhost:8312/roa'

	
## Endpoints

The following endpoints are exposed via the REST API controller ROAController.java to acheive the above mentioned functionality.
- UpdateAssistant -  PUT endpoint : /roa/v1/serviceprovider/assistant
- Updates the resource assistant

	RequestBody:	
			{
			    "assistantId":"2",
			    "asstLocLatitude":42.462526,
			    "asstLocLongitude":-83.376587
			}
	ResponseBody:
			200 OK reponse if the update is successful
			
2) FindNearByAssistants - GET endpoint : /roa/v1/customer/111/findNearestAssistants?custLocLatitude=42.496977&custLocLongitude=-83.54131
- Returns the 5 nearest service assistants if available. The list of assistants are ordered in the ascending order by their distance from the customer geo location provided in the request 
		
	Request:	
			
	Response:
			[
	    {
	        "latitude": 42.4894792,
	        "longitude": -83.5027254,
	        "assistantId": "3",
	        "assistantName": "SP_3",
	        "distanceBetweenCustandAsst": 3279.482123548074
	    },
	    {
	        "latitude": 42.5277300,
	        "longitude": -83.5143649,
	        "assistantId": "4",
	        "assistantName": "SP_4",
	        "distanceBetweenCustandAsst": 4071.0819498383144
	    },
	    {
	        "latitude": 42.4842487,
	        "longitude": -83.6509386,
	        "assistantId": "5",
	        "assistantName": "SP_5",
	        "distanceBetweenCustandAsst": 9122.940119208808
	    },
	    {
	        "latitude": 42.5389541,
	        "longitude": -83.4435605,
	        "assistantId": "6",
	        "assistantName": "SP_6",
	        "distanceBetweenCustandAsst": 9287.949244379184
	    },
	    {
	        "latitude": 42.4225029,
	        "longitude": -83.4292314,
	        "assistantId": "7",
	        "assistantName": "SP_7",
	        "distanceBetweenCustandAsst": 12386.362130906462
	    }
	]
    
3) ReserveAssistant - POST endpoint : /roa/v1/customer/reserveAssistant
- For a given customer (represented by the cutomerid ) and their location , this endpoint finds the nearest assistant and assigns them to the customer.
- The reserved assistant details are returned in the response as shown below.
- A new service request record is created with the service status as Assigned. 
- The assistant is marked/updated as reserved .
		
	RequestBody:
		  {
	    "custId": "111",
	    "custLocLat" : 42.496977,
	    "custLocLong" :-83.54131
		}
			
	Response:
		{
	    "assistantId": "3",
	    "assistantName": "SP_3",
	    "distanceBetweenCustandAsst": 3279.482123548074
		}
		
4) ReleaseAssistant - PUT endpoint : /roa/v1/customer/releaseAssistant
- For a given customer (represented by the cutomerid ) and their assistant (represented by assistantId ) , this endpoint releases the assistant from a customer. 
- Marks the status of the corresponding service request as Completed or Cancelled as sent in the request body element serviceReqStatus
		
	RequestBody:
		{
	    "custId": "111",
	    "assistantId" : "3",
	    "serviceReqStatus" : "Completed"
    
		}
			
	Response:
		200 OK response (if successful)



##Testing
 **CURL Commands **

- Once the application is up and running in local ,use the sample below curl commands to test the different endpoints that are described in the endpoints section to validate the functionality.The customer id and the customer location can be adjusted to test various scenarios. 5 customers with customerId from 111 to 555 have been added in the inital data script. The below sequence of curl commands are demonstrated for a customer id 111.
		
	curl --location --request PUT 'http://localhost:8312/roa/v1/serviceprovider/assistant' \
	--header 'Content-Type: application/json' \
	--data-raw '{
    "assistantId":"2",
    "asstLocLatitude":42.129224,
    "asstLocLongitude":-80.085059
	}'

	curl --location --request GET 'http://localhost:8312/roa/v1/customer/111/findNearestAssistants?custLocLatitude=42.496977&custLocLongitude=-83.54131'
	
	
	curl --location --request POST 'http://localhost:8312/roa/v1/customer/reserveAssistant' \
	--header 'Content-Type: application/json' \
	--data-raw '{
    "custId": "111",
    "custLocLat" : 42.496977,
    "custLocLong" :-83.54131
	}'
		
	curl --location --request PUT 'http://localhost:8312/roa/v1/customer/releaseAssistant' \
	--header 'Content-Type: application/json' \
	--data-raw '{
    "custId": "111",
    "assistantId" : "3",
    "serviceReqStatus" : "Completed"
	}'
    
 **JUNIT Tests **
- The Junit test cases for the serivce can be executed by running the test file EmergencyRoadsideAssistanceServiceApplicationTests.java as 'Junit test'. These tests are for the unit testing of the services layer by mocking the data layer.

 
