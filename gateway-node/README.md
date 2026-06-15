# Node.js API Gateway

Express gateway for the Academic Course Planning System.

## Responsibilities

- Exposes one public API base URL for the React frontend.
- Proxies requests to the Spring Boot backend.
- Preserves JSON bodies, query parameters, and `Authorization` headers.
- Uses environment variables for deployment.

## Routes

- `POST /auth/login`
- `POST /auth/signup`
- `GET /students/all`
- `POST /students/add`
- `GET /courses/all`
- `POST /courses/add`
- `GET /enrollments/all`
- `POST /enrollments/add`
- `GET /prerequisites/all`
- `POST /prerequisites/add`

## Local Run

```bash
npm install
npm run dev
```

## Environment

Copy `.env.example` to `.env` and update values:

```bash
PORT=5000
HOST=0.0.0.0
SPRING_BOOT_URL=http://localhost:8081
CORS_ORIGIN=http://localhost:5173
```

## Render

- Build command: `npm install`
- Start command: `npm start`
- Set `SPRING_BOOT_URL` to the deployed Spring Boot service URL.
- Render provides `PORT`; the gateway listens on `0.0.0.0`.
