# Todo App Backend

This is the backend service for the Todo App, developed using Java and the Spring framework. It provides RESTful APIs for managing tasks and integrates with the frontend application to offer a seamless user experience.

## Features

- *Task Management*: APIs to create, read, update, and delete tasks.
- *Data Persistence*: Utilizes a relational database to store task information.
- *API Documentation*: Comprehensive documentation for all available endpoints.

## Prerequisites

Ensure you have the following installed:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (version 11 or later)
- [Apache Maven](https://maven.apache.org/) (for build automation)
- [MySQL](https://www.mysql.com/) or another relational database

## Getting Started

1. *Clone the Repository*:

   ```bash
   git clone https://github.com/Vane-Arellano/todo-app-backend.git
   cd todo-app-backend


2.	Build the Application:

```mvn clean install```


3. Run the Application:

```mvn spring-boot:run```

The backend service will be accessible at http://localhost:9090.

API Endpoints
- GET /todos: Retrieve all todos.
- POST /todos: Create a new todo.
- GET /todos/{id}: Retrieve a specific todo by ID.
- PUT /todos/{id}: Update a todo by ID.
- PATCH /todos/changeStatus: Change status of todo.
- DELETE /todos/{id}: Delete a todo by ID.
- GET /todos/metrics: Get metrics.

For detailed API documentation, please refer to the API Documentation.

Project Structure
- src/main/java/: Contains the Java source code.
- src/main/resources/: Contains application properties and other resources.
- pom.xml: Maven configuration file.

Technologies Used
- Java: Primary programming language.
- Spring Boot: Framework for building the backend service.
- MySQL: Relational database for data storage.
- Maven: Build automation tool.
