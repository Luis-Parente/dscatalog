# DSCatalog
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://github.com/Luis-Parente/dscatalog/blob/main/LICENSE)

## Description
A RESTful API for product catalog management, built with Java and Spring Boot. It provides endpoints for authentication, product and category management, and includes Swagger documentation.

## 📋 Features
- JWT authentication with role-based access
- CRUD for products, categories, and users
- Swagger UI for API documentation
- PostgreSQL (dev) and H2 (test) profiles
- RESTful architecture with Spring Boot
- Layered architecture (Controller, Service, Repository)
  
## ✅ Requirements
- Java 21+
- Git
- PostgreSQL database (or use the embedded H2 for development or testing)
- (Optional) Postman for Endpoint testing

## 🛠️ Installation

### Clone the Repository
```bash
git clone https://github.com/your-username/dscatalog.git
cd dscatalog/backend
````
### Build the project using the Maven Wrapper:
```bash
./mvnw clean install
````
### Start the Application
```bash
./mvnw spring-boot:run
````

## 🔧 Database Configuration: PostgreSQL & H2
The application is pre-configured to support both PostgreSQL and H2, using Spring profiles (dev and test).

### Profile Management
- In src/main/resources/application.properties, you’ll find:
````properties
spring.profiles.active=
````
- Set the active profile to dev for PostgreSQL, or to test for H2 (in-memory) usage.

- You can also override it in the command line:
````bash
mvn spring-boot:run -Dspring-boot.run.profiles=target-profile
````

### PostgreSQL Configuration (application-dev.properties)
- Make sure PostgreSQL is running and the database dscatalog exists. Then configure:
````properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dscatalog
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.show-sql=true
````

### H2 Configuration for Testing (application-test.properties)
````properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=set-username
spring.datasource.password=set-password
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
spring.jpa.show-sql=true
````
- To access the H2 web console: http://localhost:8080/h2-console

## 🔍 Testing the API
You can test the API in two ways:

### 1.Swagger UI
Accessible at http://localhost:8080/swagger-ui/index.html. It provides a full list of available endpoints with detailed request/response schemas and example payloads.

### 2.Postman
Use Postman for a more flexible API testing experience.

The repository includes:

- A Postman collection (dscatalog.postman_collection.json)

- A Postman environment file (dscatalog.postman_environment.json)

💾 Import both files into Postman to get started immediately:

- Go to File > Import
- Select the .json files from the repository
- Use the environment to auto-fill base URLs and variables
- You can then send requests directly using the pre-configured endpoints and data.

## 🧰 Tech Stack
- Java 21+
- Spring Boot
- Maven
- Spring Security
- PostgreSQL (or H2 for dev/test)
- Swagger (OpenAPI)
- Postman (for API testing)
- JPA/Hibernate
