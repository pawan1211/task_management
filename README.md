# task_management

📝 README.md — Task Management REST API
📘 Project Overview

This is a Spring Boot RESTful API for managing tasks.
The system allows users to:

Create, read, update, and delete tasks

Mark tasks as complete

Validate inputs and handle errors gracefully

It follows REST principles, uses Spring Boot + JPA + Validation + PostgreSQL, and is designed for clarity and maintainability.

⚙️ Tech Stack

Java 17+

Spring Boot 3.x

Spring Web

Spring Data JPA (Hibernate)

PostgreSQL Database

Jakarta Validation (for @Valid)

JUnit 5 (for unit testing)

Maven (build tool)

🚀 Setup Instructions
1️⃣ Clone Repository
git clone https://github.com/your-username/task_management.git
cd task_management

2️⃣ Build Project
mvn clean install

3️⃣ Configure Database Connection

Edit src/main/resources/application.properties:

# ===============================
# PostgreSQL DATABASE CONFIGURATION
# ===============================
spring.datasource.url=jdbc:postgresql://localhost:5432/task_management
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# ===============================
# JPA / HIBERNATE CONFIG
# ===============================
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# ===============================
# SERVER CONFIG
# ===============================
server.port=8080

4 Run Application
mvn spring-boot:run


By default, the app runs at:

http://localhost:8080

5 Verify Database Connection

You can connect to PostgreSQL using psql or pgAdmin and run:

\c taskdb
\d


You should see a task table created automatically by Hibernate.

🧠 API Endpoints
Method	Endpoint	Description
GET	/tasks	Retrieve all tasks

GET	/tasks/{id}	Retrieve a specific task

POST	/tasks	Create a new task

PUT	/tasks/{id}	Update an existing task

DELETE	/tasks/{id}	Delete a task

PATCH	/tasks/{id}/complete	Mark a task as completed

📬 Example Request & Response
➕ Create Task (POST /tasks)

Request:

{
  "title": "Complete API Assignment",
  "description": "Implement RESTful API in Spring Boot",
  "dueDate": "2025-11-10"
}


Response (201 Created):

{
  "id": 1,
  "title": "Complete API Assignment",
  "description": "Implement RESTful API in Spring Boot",
  "status": "pending",
  "dueDate": "2025-11-10",
  "createdAt": "2025-11-01T12:00:00",
  "updatedAt": "2025-11-01T12:00:00"
}

⚠️ Validation Error Example

Request:

{
  "title": "",
  "description": ""
}


Response (400 Bad Request):

{
  "title": "Title cannot be blank",
  "description": "Description cannot be blank",
  "dueDate": "Due date cannot be null"
}

🧪 Postman Test Cases
Test Case	Method	Endpoint	Expected Result
1. Create valid task	POST	/tasks	201 Created
2. Create invalid task (empty fields)	POST	/tasks	400 Bad Request
3. Retrieve all tasks	GET	/tasks	200 OK
4. Retrieve task by valid ID	GET	/tasks/{id}	200 OK
5. Retrieve task by invalid ID	GET	/tasks/999	404 Not Found
6. Update valid task	PUT	/tasks/{id}	200 OK
7. Update invalid task	PUT	/tasks/999	404 Not Found
8. Mark as complete	PATCH	/tasks/{id}/complete	200 OK
9. Delete existing task	DELETE	/tasks/{id}	204 No Content
10. Delete invalid ID	DELETE	/tasks/999	404 Not Found
🧰 Run Unit Tests
mvn test


Example test (TaskServiceTest.java) checks:

Task creation logic

Validation rules

Completion status updates

🧩 Design Decisions & Assumptions
🏗️ Design Decisions

Entity & DTO Separation:
Used DTOs to decouple REST API from persistence layer for clean architecture.

Enum for Status:
Prevents invalid status values (pending, in_progress, completed).

Validation:
Used @Valid and @NotBlank/@NotNull annotations for request validation.

Global Exception Handling:
Centralized error handling using @RestControllerAdvice for clean error responses.

Service Layer:
All business logic is isolated in a TaskService class for reusability and testability.

PostgreSQL Database:
Chosen for persistence and scalability in real-world environments.

📋 Assumptions

All timestamps (createdAt, updatedAt) are handled automatically by the service layer.

Task status defaults to pending upon creation.

Only title, description, and due date are required for creation.

Authentication is not implemented (focus on CRUD + validation).
