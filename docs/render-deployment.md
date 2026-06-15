# Render Deployment Guide

You can deploy manually using the steps below, or use the included `render.yaml` blueprint from the repository root.

## 1. Deploy PostgreSQL

Create a PostgreSQL database on Render or use an external PostgreSQL provider.

Set the backend database variables:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

## 2. Deploy Spring Boot Backend

Render service type: Web Service

Root directory:

```text
Backend/coreservices/coreservices
```

Recommended Render option:

- Use the included backend `Dockerfile`.
- Root directory:

```text
Backend/coreservices/coreservices
```

Dockerfile path:

```text
Dockerfile
```

Alternative native Java build command:

```bash
mvn clean package -DskipTests
```

Alternative native Java start command:

```bash
java -jar target/academic-course-planning-0.0.1-SNAPSHOT.jar
```

Environment variables:

- `PORT`
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION_MS`
- `MONGODB_URI`
- `MONGODB_DATABASE`

Use JDBC format for Render PostgreSQL:

```text
SPRING_DATASOURCE_URL=jdbc:postgresql://HOST:PORT/DATABASE?sslmode=require
```

## 3. Deploy Node Gateway

Render service type: Web Service

Root directory:

```text
gateway-node
```

Build command:

```bash
npm install
```

Start command:

```bash
npm start
```

Environment variables:

- `SPRING_BOOT_URL`: deployed Spring Boot URL
- `CORS_ORIGIN`: deployed frontend URL
- `PROXY_TIMEOUT_MS`

Render provides `PORT`; the gateway listens on `0.0.0.0`.

## 4. Deploy React Frontend

Render service type: Static Site

Root directory:

```text
Frontend/frontend
```

Build command:

```bash
npm install && npm run build
```

Publish directory:

```text
dist
```

Environment variable:

- `VITE_API_BASE_URL`: deployed Node gateway URL

## 5. Final Deployment Check

After all services are live:

1. Backend health:

```text
https://YOUR_BACKEND.onrender.com/health
```

2. Gateway health:

```text
https://YOUR_GATEWAY.onrender.com/health
```

3. Swagger:

```text
https://YOUR_BACKEND.onrender.com/swagger-ui/index.html
```

4. Frontend:

```text
https://YOUR_FRONTEND.onrender.com
```

Login as an `ADMIN` user before testing add/create workflows because write APIs require the `ADMIN` role.
