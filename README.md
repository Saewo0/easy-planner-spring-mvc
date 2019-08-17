# Easy Planner

Migrate from the implementation using AWS to a version built with Java Spring framework

## Overview

### Original Thoughts:

Planning an activity is never an easy thing, especially for an activity involving many participants. The host has to
choose the destination, the start & end datetime, and notify every participant. Even if the consensus is reached,
everybody has to find some way to record the event like taking a paper note or creating a calendar notice, which is
extremely annoying and inconvenient.

This application is created to greatly simplify such a process and make activity planning much easier. The application
offers multiple functionality including user registration and authentication, event creation, invitation broadcast,
photo sharing, and friends adding & removing.

### Technical Overview:

This application is built upon Java Spring framework, including Spring MVC for building major web services,
and SpringBoot for implementing different functional worker servers.

The application also relies on the following parts:

* Database:

    **PostgreSQL**

* Tools & Plugins:

    **Maven** for dependency management & building automation
    
    **Flyway** for database schema migration
    
    **Apache DBCP** for database connection management

    **Hibernate** for ORM
    
    **JWT token** for stateless authentication
    
    **JUnit**, **Mockito** for unit test

    **Postman** for API testing
    
    **Docker** for holding servers
    
* Third-party Services
    
    **Amazon SQS & S3**
    
    **SendGrid Template Email Service**

### Database Schema:

* Entities: User, Event, Photo, Invitation

* Entity Relationships:
    
    1. One to many between User and Photo, User and Invitation.
    
    2. Many to many between User and Event, User and User.
    
### Developing Logs:

1. Configure Hibernate to do ORM between the application and the database

2. Create domain models for each entity User, Event, Photo, and Invitation

3. Create repositories (DAO class) for each model and corresponding unit tests

4. Implement service layers for each model based on DAO classes to extend functionality

5. Design controllers according to API needs

6. Configure Spring Security and modify User entity for Authentication and Authorization

7. Integrate third-party services such as Amazon SQS & S3, and do mock tests [Check here](https://blog.frugalops.com/integrate-amazon-sqs-and-sendgrid-with-springboot-application/)

8. Create worker applications to process messages from SQS

9. Use Postman to test all APIs

10. Package both parts of the project into Docker images for future deployment.

To be continued...

## Environment Setup

### Start Database using Docker & DockerHub

```
docker pull postgres
docker run --name ${PostgreSQL_Container_Name} -e POSTGRES_USER=${USERNAME} -e POSTGRES_PASSWORD=${PASSWORD}
-e POSTGRES_DB=${DB_NAME} -p ${HOST_PORT}:${CONTAINER_PORT} -d postgres
```

### Environment Property Configuration

```
location:./mvc/src/main/resources/META-INF/env/applition-xxx.propertities
   
Template:
database.driverName=${driverName}
database.url=${url}
database.port=${port}
database.name=${name}
database.username=${username}
database.password=${password}
   
mvn compile -Dspring.profiles.active=${env}
```

### Flyway Configuration

```
location: ./mvc/pom.xml line 25-72

-Ddb_url=localhost:${HOST_PORT}/${databasename} -Ddb_username=${db_username} -Ddb_password=${db_password}

Usage:
mvn compile flyway:migrate -P ${PROFILE_NAME}
```

### Testing
* Package and install the folder before unit test.
    
    ```mvn clean compile install -DskipTests=true```

* Tests are done using JUnit and Mockito. Tests are run using the command:
    ```
    mvn compile test -Dspring.profiles.active=${env} -P ${env}
    mvn compile test -Dspring.profiles.active=${unit} -P ${unit}
    ```

## Package and Production

### Generate .war file

```
mvn compile package -P dev -DskipTests=true
```

### Deploy to Tomcat

```
mv ./easy-planner-mvc-0.0.1-SNAPSHOT.war ${PATH_TO_TOMCAT}/VERSION/libexec/webapps/ROOT.war
cd ${PATH_TO_TOMCAT}/VERSION/libexec/
vim bin/setenv.sh

add all configuration to setenv.sh:

Template:
export CATALINA_OPTS="$CATALINA_OPTS -Xms1g"
export CATALINA_OPTS="$CATALINA_OPTS -Dspring.profiles.active=dev"
...

deploy and run:
sh startup.sh

shutdown:
sh shutdown.sh
```

## Reference Demo
### User sign up
```$xslt
POST - http://localhost:8080/api/users/signup
```
Requestbody
```
{
	"firstName":"testFN",
	"lastName":"testLN",
	"username":"username4",
	"email":"testEmail4",
	"password":"testPassword",
	"phone":"99999999"
}
```

 Responsebody
```
{
    "firstName": "testFN",
    "lastName": "testLN",
    "username": "username4",
    "email": "testEmail4",
    "phone": "99999999",
    "password": "$2a$10$WJi7R6dAlFfFnlxLH3OX4.84YVQy3iPps7cmQ1INPWatvT6XxSnCS",
    "id": 22,
    "enabled": true,
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true
}
```
Postman snapshoot for user sign up
### User login

```
POST http://localhost:8080/api/users/login
```
 Requestbody
 ```
{ 
	"username": "testusername3",
	"password": "testPassword"
}
```
Responsebody
```
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlcm5hbWUzIiwiYXVkaWVuY2UiOiJhdWRpZW5jZSIsImNyZWF0ZWQiOjE1NTMwMTEyNTE4NzcsImV4cCI6MTU1MzA5NzY1MX0.jV0e6fHC1JLxSfoNtAaVqL4ouKyNswnA3m4oGHCVj5V6vdrwmHB0glAx-E2Dxgxg0BXgdLU2eUgrrWbMZSjr1A"
}
```

Postman snapshoot for user login

### post image to AWS S3

```$xslt
POST http://localhost:8080/api/image
```
Postman snapshoot for uploading a image to S3

### send message to AWS SQS

```
POST http://localhost:8080/api/message
```

Postman snapshoot for sending messages to SQS

