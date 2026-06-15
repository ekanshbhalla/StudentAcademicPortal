package com.klu.mongolog;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UserActivityLog")
public class UserActivityLog {

    @Id
    private String id;

    private String username;
    private String action;
    private Instant timestamp;
    private String ipAddress;

    public UserActivityLog() {
    }

    public UserActivityLog(String username, String action, Instant timestamp, String ipAddress) {
        this.username = username;
        this.action = action;
        this.timestamp = timestamp;
        this.ipAddress = ipAddress;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
