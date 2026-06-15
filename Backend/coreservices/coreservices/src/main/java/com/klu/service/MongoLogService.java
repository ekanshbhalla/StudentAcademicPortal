package com.klu.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.mongolog.EnrollmentLog;
import com.klu.mongolog.EnrollmentLogRepository;
import com.klu.mongolog.LoginLog;
import com.klu.mongolog.LoginLogRepository;
import com.klu.mongolog.UserActivityLog;
import com.klu.mongolog.UserActivityLogRepository;

@Service
public class MongoLogService {

    @Autowired
    private UserActivityLogRepository userActivityLogRepository;

    @Autowired
    private EnrollmentLogRepository enrollmentLogRepository;

    @Autowired
    private LoginLogRepository loginLogRepository;

    public void logUserActivity(String username, String action, String ipAddress) {
        try {
            userActivityLogRepository.save(
                    new UserActivityLog(username, action, Instant.now(), ipAddress)
            );
        } catch (Exception exception) {
            System.out.println("MongoDB user activity logging skipped: " + exception.getMessage());
        }
    }

    public void logEnrollment(Long studentId, Long courseId, String action) {
        try {
            enrollmentLogRepository.save(
                    new EnrollmentLog(studentId, courseId, action, Instant.now())
            );
        } catch (Exception exception) {
            System.out.println("MongoDB enrollment logging skipped: " + exception.getMessage());
        }
    }

    public void logLogin(String username, String status) {
        try {
            loginLogRepository.save(
                    new LoginLog(username, Instant.now(), status)
            );
        } catch (Exception exception) {
            System.out.println("MongoDB login logging skipped: " + exception.getMessage());
        }
    }
}
