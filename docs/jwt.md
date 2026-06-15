# JWT Documentation

JWT support is implemented in Spring Boot:

- `AuthController` authenticates users and returns tokens.
- `JwtUtil` generates and validates tokens.
- `JwtFilter` reads `Authorization: Bearer <token>` and sets Spring Security authentication.
- `SecurityConfig` runs the application as stateless and registers the JWT filter.

## Token Generation

Tokens include:

- subject: username
- role: `ADMIN`, `STUDENT`, or another configured user role
- issued at timestamp
- expiration timestamp
- HMAC signature using `JWT_SECRET`

## Token Validation

The backend validates:

- token signature
- token structure
- token expiration

Invalid or expired bearer tokens return:

```json
{ "message": "Invalid or expired token" }
```

## Route Security

- `/auth/**`, `/health`, `/swagger-ui/**`, and `/v3/api-docs/**` are public.
- `GET /students/**`, `/courses/**`, `/enrollments/**`, and `/prerequisites/**` require login.
- `POST /students/**`, `/courses/**`, `/enrollments/**`, and `/prerequisites/**` require role `ADMIN`.

The React frontend sends the stored JWT automatically through the shared API client.
