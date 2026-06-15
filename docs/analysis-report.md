# Project Analysis Report

## Current Architecture

The project is a three-part academic planning system:

- `Frontend/frontend`: React + Vite single-page app.
- `Backend/coreservices/coreservices`: Spring Boot REST backend.
- `Backend/gateway`: existing FastAPI gateway.

This update adds:

- `gateway-node`: production-ready Express gateway.
- MongoDB logging support in Spring Boot.
- Root deployment and environment documentation.

## Backend Structure

Controllers:

- `AuthController`: signup and login.
- `StudentController`: add/list students.
- `CourseController`: add/list courses.
- `EnrollmentController`: add/list enrollments.
- `PrerequisiteController`: add/list prerequisites.

Services:

- `StudentService`
- `CourseService`
- `EnrollmentService`
- `PrerequisiteService`
- `MongoLogService`

Repositories:

- SQL JPA repositories for `User`, `Student`, `Course`, `Enrollment`, and `Prerequisite`.
- Mongo repositories for `UserActivityLog`, `EnrollmentLog`, and `LoginLog`.

## Database Structure

The SQL database is PostgreSQL. The provided dump contains:

- `users`
- `students`
- `courses`
- `enrollments`
- `prerequisites`

Relationships:

- `enrollments.student_id` references `students.student_id`.
- `enrollments.course_id` references `courses.course_id`.
- `prerequisites.course_id` references `courses.course_id`.
- `prerequisites.prerequisite_course_id` references `courses.course_id`.

MongoDB is used only for logs and analytics. No SQL entity was migrated to MongoDB.

## JWT Implementation

Before:

- Secret was hardcoded in `JwtUtil`.
- Token expiry existed but config was not environment-driven.
- Invalid token handling only printed to stdout.

Now:

- `JWT_SECRET` and `JWT_EXPIRATION_MS` are configurable.
- Expired and invalid tokens are handled explicitly.
- Invalid bearer tokens return a `401` JSON response.
- Current public route behavior is preserved for compatibility.

## Frontend API Flow

Before:

- Pages called `http://localhost:8081` directly.

Now:

- Pages use `VITE_API_BASE_URL`, defaulting to `http://localhost:5000`.
- The frontend can target the Node gateway locally and in production.

## Gateway Layer

The existing FastAPI gateway proxies auth, student, course, enrollment, and prerequisite routes to Spring Boot. It now uses `SPRING_BOOT_URL`, forwards authorization headers, includes timeout/error handling, and exposes `/health`.

The Node gateway is also environment-driven and proxies all required routes, including auth. For production, use the Node gateway as the main deployed API gateway unless your evaluator specifically requires FastAPI.

## Deployment Limitations Found

- Backend database credentials were hardcoded.
- JWT secret was hardcoded.
- Frontend had hardcoded localhost API URLs.
- Existing FastAPI gateway previously had hardcoded backend URL and incomplete route coverage; this has been fixed.
- No root deployment documentation existed.
- Maven is not available on the current machine PATH, but IntelliJ's bundled Maven successfully builds the backend.
