# JWT Authentication Backend

This project implements JWT authentication using Spring Boot, Spring Security, Spring Data JPA, and an in-memory H2 database.

## Features

- User registration with encrypted passwords
- Login with username and password
- JWT token generation for authenticated sessions
- Protected route access using `Authorization: Bearer <token>`
- Logout with token invalidation using an in-memory blacklist
- Seeded demo account for quick testing in Postman

## Tech Stack

- Java 17
- Spring Boot 3.2.5
- Spring Security
- Spring Data JPA
- H2 Database
- JJWT

## Project Structure

```text
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── security/
│   │   └── service/
│   └── resources/
└── test/
docs/
└── screenshots/
```

## Default Test User

- Username: `admin`
- Password: `1234`

This user is created automatically when the application starts.

## API Endpoints

### `POST /auth/register`

Registers a new user.

Request body:

```json
{
  "username": "alice",
  "password": "password123"
}
```

### `POST /auth/login`

Authenticates a user and returns a JWT token.

Request body:

```json
{
  "username": "admin",
  "password": "1234"
}
```

Response:

```json
{
  "token": "<jwt-token>",
  "username": "admin",
  "message": "Login successful"
}
```

### `GET /auth/protected`

Protected endpoint. Requires a valid bearer token.

Header:

```text
Authorization: Bearer <jwt-token>
```

### `POST /auth/logout`

Invalidates the current token by adding it to the server-side blacklist.

Header:

```text
Authorization: Bearer <jwt-token>
```

## How to Run

### 1. Start the application

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

### 2. Test in Postman

1. Send a `POST` request to `http://localhost:8080/auth/login` with the demo credentials.
2. Copy the JWT token from the response.
3. Send a `GET` request to `http://localhost:8080/auth/protected` with the `Authorization` header set to `Bearer <token>`.
4. Send a `POST` request to `http://localhost:8080/auth/logout` with the same `Authorization` header.
5. Retry `GET /auth/protected` using the same token to show invalidation.

## Required Screenshots

Store screenshots inside [docs/screenshots](/C:/Users/monda/warehouse/fsd2/Exp6/docs/screenshots).

At minimum capture:

1. Successful login request showing the JWT token in the response.
2. Protected route accessed successfully using the token.
3. Logout request or protected route failure after logout showing token invalidation.

Suggested filenames:

- `01-login-success.png`
- `02-protected-route-success.png`
- `03-logout-token-invalidated.png`

## Notes

- Passwords are hashed using BCrypt.
- JWT tokens expire after 1 hour.
- Logout invalidation is in-memory, which is sufficient for this assignment/demo but not ideal for distributed production deployments.
