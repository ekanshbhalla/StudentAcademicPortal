# API Reference

All routes are exposed by the Node gateway and forwarded to Spring Boot.

Protected academic routes require:

```text
Authorization: Bearer <jwt-token>
```

Read routes require any valid logged-in user. Write routes require role `ADMIN`.

## Auth

- `POST /auth/login`
  - Body: `{ "username": "admin", "password": "admin123" }`
  - Response: `{ "token": "...", "role": "ADMIN", "message": "Login Successful" }`

- `POST /auth/signup`
  - Body: `{ "username": "student1", "password": "password", "role": "STUDENT" }`
  - Response: `{ "message": "Signup Successful" }`

## Students

- `GET /students/all`
- `POST /students/add`
  - Body: `{ "name": "Name", "email": "email@example.com", "department": "CSE", "semester": 3 }`

## Courses

- `GET /courses/all`
- `POST /courses/add`
  - Body: `{ "courseCode": "CSE101", "courseName": "Database Management Systems", "credits": 4, "department": "CSE" }`

## Enrollments

- `GET /enrollments/all`
- `POST /enrollments/add`
  - Body:

```json
{
  "student": { "studentId": 1 },
  "course": { "courseId": 1 },
  "semester": 1,
  "year": 2026,
  "status": "ACTIVE"
}
```

## Prerequisites

- `GET /prerequisites/all`
- `POST /prerequisites/add`
  - Body:

```json
{
  "course": { "courseId": 1 },
  "prerequisiteCourse": { "courseId": 2 }
}
```
