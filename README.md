# Java Web Application - Spring Boot CRUD REST API

A Spring Boot application demonstrating a CRUD REST API for User management with layered architecture. Supports both in-memory storage and PostgreSQL database via JPA.

## Features

- RESTful API for User CRUD operations
- Layered architecture (Controller, Service, Repository)
- Support for in-memory storage (using Java Collections)
- PostgreSQL integration via Spring Data JPA
- Multiple environment profiles (dev, prod, memory)
- Lombok for reducing boilerplate code
- Log4j2 for logging
- JUnit tests

## Technologies

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Web** - RESTful web services
- **Spring Data JPA** - Database access
- **PostgreSQL** - Relational database
- **Lombok** - Reduce boilerplate code
- **Log4j2** - Logging framework
- **Gradle** - Build tool
- **JUnit 5** - Testing framework

## Project Structure

```
src/main/java/com/example/webapp/
├── WebApplication.java              # Main Spring Boot application
├── controller/
│   └── UserController.java          # REST API endpoints
├── service/
│   └── UserService.java             # Business logic layer
├── repository/
│   ├── UserRepository.java          # JPA repository interface
│   └── InMemoryUserRepository.java  # In-memory repository implementation
└── model/
    └── User.java                     # User entity/model

src/main/resources/
├── application.yml                   # Default configuration
├── application-dev.yml               # Development profile
├── application-prod.yml              # Production profile
└── application-memory.yml            # In-memory profile
```

## Getting Started

### Prerequisites

- Java 17 or higher
- PostgreSQL (only for database mode)

### Building the Application

```bash
./gradlew build
```

### Running the Application

#### Option 1: In-Memory Mode (No database required)

```bash
./gradlew bootRun --args='--spring.profiles.active=memory'
```

#### Option 2: Development Mode (PostgreSQL required)

1. Start PostgreSQL and create a database:
```sql
CREATE DATABASE webapp_db_dev;
```

2. Update database credentials in `src/main/resources/application-dev.yml` if needed

3. Run the application:
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

#### Option 3: Production Mode

1. Set environment variables:
```bash
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
```

2. Run the application:
```bash
./gradlew bootRun --args='--spring.profiles.active=prod'
```

## API Endpoints

The API is available at `http://localhost:8080/api/users`

### Create User
```bash
POST /api/users
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "age": 30
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "age": 30
}
```

### Get All Users
```bash
GET /api/users
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "age": 30
  }
]
```

### Get User by ID
```bash
GET /api/users/{id}
```

**Response:** `200 OK` or `404 Not Found`

### Update User
```bash
PUT /api/users/{id}
Content-Type: application/json

{
  "name": "John Updated",
  "email": "john.updated@example.com",
  "age": 31
}
```

**Response:** `200 OK` or `404 Not Found`

### Delete User
```bash
DELETE /api/users/{id}
```

**Response:** `204 No Content` or `404 Not Found`

## Testing the API

### Using curl

```bash
# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","age":30}'

# Get all users
curl -X GET http://localhost:8080/api/users

# Get user by ID
curl -X GET http://localhost:8080/api/users/1

# Update user
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Jane Doe","email":"jane@example.com","age":25}'

# Delete user
curl -X DELETE http://localhost:8080/api/users/1
```

## Running Tests

```bash
./gradlew test
```

## Configuration Profiles

### Memory Profile (`application-memory.yml`)
- No database required
- Uses in-memory Java collections (ConcurrentHashMap)
- Perfect for development and testing
- Data is lost when application stops

### Dev Profile (`application-dev.yml`)
- Uses PostgreSQL database
- Database: `webapp_db_dev`
- DDL auto: `create-drop` (recreates schema on startup)
- SQL logging enabled
- Debug level logging

### Prod Profile (`application-prod.yml`)
- Uses PostgreSQL database
- Database credentials from environment variables
- DDL auto: `validate` (validates schema only)
- Minimal logging
- Production-ready settings

## Architecture

The application follows a layered architecture pattern:

1. **Controller Layer** (`UserController`): Handles HTTP requests and responses
2. **Service Layer** (`UserService`): Contains business logic
3. **Repository Layer** (`UserRepository`, `InMemoryUserRepository`): Handles data access

This separation of concerns makes the application:
- Easy to test
- Easy to maintain
- Easy to extend
- Flexible (can switch between in-memory and database storage)

## Database Schema

When using PostgreSQL, the User table has the following structure:

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    age INTEGER
);
```

## Switching Between In-Memory and PostgreSQL

The application automatically switches between in-memory and PostgreSQL based on the active profile:

- **memory** profile: Uses `InMemoryUserRepository`
- **dev/prod** profiles: Uses `UserRepository` (JPA with PostgreSQL)

This is managed by the `UserService` which checks which repository bean is available.

## License

This project is for educational purposes.