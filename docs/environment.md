# Environment Variables

## Spring Boot Backend

| Variable | Purpose | Local Default |
| --- | --- | --- |
| `PORT` | HTTP port used by Spring Boot | `8081` |
| `SPRING_DATASOURCE_URL` | PostgreSQL JDBC URL | `jdbc:postgresql://localhost:9122/academic_planning` |
| `SPRING_DATASOURCE_USERNAME` | SQL database username | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | SQL database password | `123456` |
| `JWT_SECRET` | HMAC signing secret for JWTs | local development secret |
| `JWT_EXPIRATION_MS` | JWT lifetime in milliseconds | `86400000` |
| `MONGODB_URI` | MongoDB connection URI | `mongodb://localhost:27017/academic_planning_logs?serverSelectionTimeoutMS=1000` |
| `MONGODB_DATABASE` | MongoDB database name | `academic_planning_logs` |

Use a long random `JWT_SECRET` in production. For HS256, keep it at least 32 characters.

## Node Gateway

| Variable | Purpose | Local Default |
| --- | --- | --- |
| `PORT` | Gateway HTTP port | `5000` |
| `HOST` | Bind host | `0.0.0.0` |
| `SPRING_BOOT_URL` | Backend service URL | `http://localhost:8081` |
| `CORS_ORIGIN` | Allowed frontend origin | `http://localhost:5173` |
| `PROXY_TIMEOUT_MS` | Backend request timeout | `15000` |

## React Frontend

| Variable | Purpose | Local Default |
| --- | --- | --- |
| `VITE_API_BASE_URL` | Public gateway/API base URL | `http://localhost:5000` |

## FastAPI Gateway

| Variable | Purpose | Local Default |
| --- | --- | --- |
| `SPRING_BOOT_URL` | Backend service URL | `http://localhost:8081` |
| `REQUEST_TIMEOUT_SECONDS` | Backend request timeout | `15` |
