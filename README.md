# University Dormitory Management System

A Java-based REST API for managing university accommodation. The backend models universities, dormitories (foyers), blocks, rooms, students, and room reservations, and exposes endpoints to manage these resources.

## Overview

The application is built with **Spring Boot** and **Spring Data JPA**. It uses **MySQL** for persistence and follows a layered structure with controllers, services, repositories, entities, and DTOs.

The project is intended to support common university housing workflows, including assigning dormitories to universities, organizing rooms into blocks, registering students, and creating or managing accommodation reservations.

## Main Features

- **University management:** create, retrieve, update, and delete universities.
- **Dormitory management:** manage dormitories and link a dormitory to a university.
- **Block and room management:** organize rooms within blocks and represent room types (`SIMPLE`, `DOUBLE`, and `TRIPLE`).
- **Student management:** maintain student records and retrieve a student's reservation history.
- **Reservation management:** create, retrieve, update, validate, cancel, and delete reservations.
- **Availability and capacity checks:** expose room availability and room-capacity information.
- **Reservation statistics:** retrieve reservation counts over a specified academic-year date range.
- **Chat assistant endpoint:** provide simple keyword-based responses through a chat API endpoint.
- **API error handling:** return structured error responses for validation failures and common request errors.

## Technology Stack

- **Language:** Java 21
- **Framework:** Spring Boot 4
- **Web API:** Spring MVC / REST
- **Persistence:** Spring Data JPA, Hibernate
- **Database:** MySQL
- **Build tool:** Maven (Maven Wrapper included)
- **Other libraries:** Lombok, Jakarta Validation
- **Testing:** JUnit 5, Spring Boot Test, Mockito, MockMvc

## Project Structure

```text
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── config/          # Application configuration
│   │   ├── controller/      # REST API controllers
│   │   ├── dto/             # Data transfer objects
│   │   ├── entity/          # JPA entities and relationships
│   │   ├── exception/       # Global API exception handling
│   │   ├── repository/      # Spring Data repositories
│   │   ├── service/         # Service interfaces
│   │   └── serviceImpl/     # Business logic
│   └── resources/
│       ├── application.properties
│       └── db/migration/    # SQL migration scripts
└── test/
    └── java/                # Unit and integration tests
```

## Prerequisites

Before running the application, install:

- JDK 21
- MySQL Server
- A Git client (optional, for cloning the repository)

## Configuration

1. Create a MySQL database named `foyer_db`.
2. Open `src/main/resources/application.properties`.
3. Configure the database connection for your local MySQL installation.

The current configuration expects a local MySQL server on port `3306` and a database named `foyer_db`. Update the username and password to match your own environment. Do not commit real database passwords or other secrets to GitHub.

Example configuration:

```properties
spring.application.name=foyer-management
spring.datasource.url=jdbc:mysql://localhost:3306/foyer_db
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
server.port=8080
```

## Run the Application

### Windows

From the project root directory, open Command Prompt or PowerShell and run:

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

The API is configured to run at:

```text
http://localhost:8080
```

## API Overview

The API uses JSON request and response bodies. The main resource routes are:

| Resource | Base endpoint | Purpose |
|---|---|---|
| Universities | `/universite` | Manage universities |
| Dormitories | `/foyer` | Manage dormitories and university links |
| Blocks | `/bloc` | Manage dormitory blocks |
| Rooms | `/chambre` | Manage rooms and room capacity |
| Students | `/etudiant` | Manage student records |
| Reservations | `/reservation` | Manage and validate reservations |
| Chat assistant | `/api/ai/chat` | Return a keyword-based chat response |

Most resource endpoints support the usual `GET`, `POST`, `PUT`, and `DELETE` operations. Some resources also provide additional operations, such as room availability, reservation validation/cancellation, and student reservation history.

For endpoint details and example request bodies, see **[ENDPOINTS.md](ENDPOINTS.md)**.

## Run Tests

Run the test suite with the Maven Wrapper:

### Windows

```powershell
.\mvnw.cmd test
```

### Linux / macOS

```bash
./mvnw test
```

The repository includes tests for reservation service behavior and global API exception handling.

## Notes

- Room types are represented by the `TypeChambre` enum: `SIMPLE`, `DOUBLE`, and `TRIPLE`.
- Reservation requests use student and room identifiers to associate accommodation records.
- The chat endpoint currently uses basic keyword matching; it is not connected to an external generative AI service.
- Make sure your local MySQL service is running and the database credentials are configured before starting the application.

## Author

**Chahbani Omar**

GitHub: [@chahbaniomar](https://github.com/chahbaniomar)

---

*Academic project — University Dormitory Management System.*
