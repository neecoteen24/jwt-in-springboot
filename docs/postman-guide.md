# Postman Guide

Use these requests in order after starting the app with:

```powershell
.\mvnw.cmd spring-boot:run
```

Base URL:

```text
http://localhost:8080
```

## 1. Login and capture JWT

- Method: `POST`
- URL: `http://localhost:8080/auth/login`
- Headers:
  - `Content-Type: application/json`
- Body:

```json
{
  "username": "admin",
  "password": "1234"
}
```

Expected response:

```json
{
  "token": "<jwt-token>",
  "username": "admin",
  "message": "Login successful"
}
```

Take screenshot `01-login-success.png`.

## 2. Access protected route

- Method: `GET`
- URL: `http://localhost:8080/auth/protected`
- Headers:
  - `Authorization: Bearer <paste-token-here>`

Expected response:

```json
{
  "message": "You accessed a protected route as admin"
}
```

Take screenshot `02-protected-route-success.png`.

## 3. Logout

- Method: `POST`
- URL: `http://localhost:8080/auth/logout`
- Headers:
  - `Authorization: Bearer <same-token>`

Expected response:

```json
{
  "message": "Token invalidated successfully"
}
```

## 4. Show token invalidation

Repeat the protected route request using the same token:

- Method: `GET`
- URL: `http://localhost:8080/auth/protected`
- Headers:
  - `Authorization: Bearer <same-token>`

Expected response:

- Status: `401 Unauthorized`
- Body contains: `Token has been invalidated`

Take screenshot `03-logout-token-invalidated.png`.
