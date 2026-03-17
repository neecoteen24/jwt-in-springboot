# JWT Authentication Backend using Spring Boot

## Project Overview

This project is a backend authentication system developed using Spring Boot. It implements JSON Web Token (JWT) based authentication for user session management and route protection. The application supports user registration, user login, JWT generation, validation of authenticated requests, and token invalidation through logout.

The objective of this project is to demonstrate how stateless authentication can be implemented in a REST API using Spring Security and JWT.

## Objective

The backend was developed to fulfill the following requirements:

- Implement JWT authentication in a Spring Boot application
- Authenticate users using username and password
- Generate JWT tokens for valid sessions
- Protect secured routes using bearer token authentication
- Demonstrate authentication flow through Postman

## Technology Stack

- Java 17
- Spring Boot 3.2.5
- Spring Security
- Spring Data JPA
- H2 In-Memory Database
- JJWT library for token generation and validation
- Maven

## Project Structure

```text
src/
|-- main/
|   |-- java/com/example/demo/
|   |   |-- config/
|   |   |-- controller/
|   |   |-- dto/
|   |   |-- model/
|   |   |-- repository/
|   |   |-- security/
|   |   `-- service/
|   `-- resources/
|-- test/
docs/
`-- screenshots/
```

## Authentication Flow

The authentication process in this project works as follows:

1. A user sends credentials to the login endpoint.
2. The backend verifies the username and password using Spring Security.
3. If authentication is successful, a JWT token is generated and returned.
4. The client includes this token in the `Authorization` header as `Bearer <token>` for protected requests.
5. A custom JWT filter validates the token before allowing access to secured routes.
6. During logout, the token is stored in an in-memory blacklist so it can no longer be used.

This design demonstrates stateless authentication because the server does not maintain a traditional HTTP session.

## Main Components

### Controller Layer

The controller layer exposes the API endpoints for authentication and protected access.

- `POST /auth/register` registers a new user
- `POST /auth/login` authenticates a user and returns a JWT token
- `GET /auth/protected` verifies access to a protected route
- `POST /auth/logout` invalidates the current token
- `GET /` provides a simple API status response

### Service Layer

The service layer handles:

- user registration
- login validation
- JWT generation
- logout token invalidation
- loading users from the database

### Security Layer

The security layer contains:

- `SecurityConfig` for Spring Security configuration
- `JwtUtil` for token generation and validation
- `JwtAuthenticationFilter` for extracting and validating bearer tokens from requests

### Persistence Layer

The persistence layer uses JPA and H2 database.

- `User` entity stores username, password, and role
- `UserRepository` performs database access

## Database and Demo User

The application uses an H2 in-memory database. A default demo user is inserted automatically at startup for testing purposes.

- Username: `admin`
- Password: `1234`

Passwords are stored in encrypted form using BCrypt.

## API Endpoints

### 1. Register User

**Endpoint:** `POST /auth/register`

**Request Body**

```json
{
  "username": "alice",
  "password": "password123"
}
```

**Purpose**

Creates a new user account in the database.

### 2. Login User

**Endpoint:** `POST /auth/login`

**Request Body**

```json
{
  "username": "admin",
  "password": "1234"
}
```

**Sample Response**

```json
{
  "token": "<jwt-token>",
  "username": "admin",
  "message": "Login successful"
}
```

**Purpose**

Authenticates the user and returns a JWT token for further requests.

### 3. Access Protected Route

**Endpoint:** `GET /auth/protected`

**Header**

```text
Authorization: Bearer <jwt-token>
```

**Sample Response**

```json
{
  "message": "You accessed a protected route as admin"
}
```

**Purpose**

Demonstrates that access is granted only when a valid token is supplied.

### 4. Logout

**Endpoint:** `POST /auth/logout`

**Header**

```text
Authorization: Bearer <jwt-token>
```

**Sample Response**

```json
{
  "message": "Token invalidated successfully"
}
```

**Purpose**

Invalidates the active token so it cannot be reused.

## Security Features Implemented

- Stateless authentication using JWT
- Password encryption using BCrypt
- Route protection using Spring Security
- Token validation through a custom filter
- Token invalidation during logout using blacklist logic
- One-hour token expiration

## Postman Demonstration

The authentication flow was tested using Postman. The required screenshots for submission are stored in [docs/screenshots](/C:/Users/monda/warehouse/fsd2/Exp6/docs/screenshots).

The screenshots demonstrate:

1. Successful login request and JWT token generation
2. Successful access to a protected route using the token
3. Logout and token invalidation behavior

Detailed Postman request flow is documented in [postman-guide.md](/C:/Users/monda/warehouse/fsd2/Exp6/docs/postman-guide.md).

## How to Run the Project

### Using PowerShell on Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### Application Base URL

```text
http://localhost:8080
```

## Testing

The project includes automated tests to verify:

- application context loading
- successful login
- access to protected route using JWT
- token invalidation after logout

Tests were executed successfully using:

```powershell
.\mvnw.cmd test
```

## Conclusion

This project demonstrates a complete JWT-based authentication workflow in Spring Boot. It includes authentication, authorization, protected endpoint access, and logout handling. The implementation shows the practical use of Spring Security with JWT for secure backend session management in REST APIs.
