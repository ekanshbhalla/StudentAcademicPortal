# MongoDB Logs and Analytics

MongoDB is added only for logs and analytics. Relational academic data remains in PostgreSQL.

## Collections

### UserActivityLog

Fields:

- `username`
- `action`
- `timestamp`
- `ipAddress`

Written for signup and login activity.

### EnrollmentLog

Fields:

- `studentId`
- `courseId`
- `action`
- `timestamp`

Written when a new enrollment is created.

### LoginLog

Fields:

- `username`
- `loginTime`
- `status`

Written for successful and failed login attempts.

## Failure Behavior

MongoDB logging is best-effort. If MongoDB is unavailable, the backend logs a message and continues the PostgreSQL-backed operation.

