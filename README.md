# Java Web Application - Spring Boot CRUD REST API

A Spring Boot application demonstrating a CRUD REST API for User management with layered architecture. Supports both in-memory storage and PostgreSQL database via JPA.

## Features

- RESTful API for User CRUD operations
- Layered architecture (Controller, Service, Repository)
- PostgreSQL integration via Spring Data JPA
- Lombok for reducing boilerplate code
- JUnit tests

## Technologies

- **Java 24**
- **Spring Boot 3.2.0**
- **Spring Web** - RESTful web services
- **Spring Data JPA** - Database access
- **PostgreSQL** - Relational database
- **Lombok** - Reduce boilerplate code
- **Gradle** - Build tool
- **JUnit 5** - Testing framework

## Getting Started

### Prerequisites

- Java 24 or higher
- PostgreSQL

### Building the Application

1. Set environment variables:
```bash
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
```

2. Run the application:
```bash
./gradlew bootRun --args='--spring.profiles.active=prod'
```

## Testing the API

### Using curl

```bash
# Create a user
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"username":"John Doe","password":"john@example.com"}'

# Get user by ID
curl -X GET http://localhost:8080/users/1

```

## Running Tests

```bash
./gradlew test
```
## License

This project is for educational purposes.