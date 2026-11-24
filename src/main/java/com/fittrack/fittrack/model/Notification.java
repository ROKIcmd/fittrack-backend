package com.fittrack.fittrack.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Type {
        MEMBERSHIP_RENEWED,
        METRICS_UPDATED,
        RESCHEDULE_APPROVED,
        RESCHEDULE_DECLINED,
        SESSION_UPDATED
    }

    private final String id;
    private final String memberId;
    private final Type type;
    private final String title;
    private final String message;
    private final LocalDateTime createdAt;
    private boolean read;

    public Notification(String id, String memberId, Type type, String title, String message, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = memberId;
        this.type = type;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
        this.read = false;
    }

    public void markRead() { this.read = true; }

    public String getId() { return id; }
    public String getMemberId() { return memberId; }
    public Type getType() { return type; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isRead() { return read; }
}