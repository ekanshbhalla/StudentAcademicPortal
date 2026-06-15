package com.klu.mongolog;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "LoginLog")
public class LoginLog {

    @Id
    private String id;

    private String username;
    private Instant loginTime;
    private String status;

    public LoginLog() {
    }

    public LoginLog(String username, Instant loginTime, String status) {
        this.username = username;
        this.loginTime = loginTime;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Instant loginTime) {
        this.loginTime = loginTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
