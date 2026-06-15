package com.klu.mongolog;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "EnrollmentLog")
public class EnrollmentLog {

    @Id
    private String id;

    private Long studentId;
    private Long courseId;
    private String action;
    private Instant timestamp;

    public EnrollmentLog() {
    }

    public EnrollmentLog(Long studentId, Long courseId, String action, Instant timestamp) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
