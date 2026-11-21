package com.fittrack.fittrack.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class RescheduleRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Status { PENDING, APPROVED, DECLINED }

    private String id;
    private String sessionId;
    private String memberId;
    private String trainerId;
    private LocalDate requestedDate;
    private LocalTime requestedTime;
    private String reason;
    private String trainerReply;
    private LocalDateTime createdAt;
    private Status status;

    public RescheduleRequest(String id, String sessionId, String memberId, String trainerId,
                             LocalDate requestedDate, LocalTime requestedTime, String reason) {
        this.id = id;
        this.sessionId = sessionId;
        this.memberId = memberId;
        this.trainerId = trainerId;
        this.requestedDate = requestedDate;
        this.requestedTime = requestedTime;
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
        this.status = Status.PENDING;
    }

    public void approve(String trainerReply) {
        this.trainerReply = trainerReply;
        this.status = Status.APPROVED;
    }

    public void decline(String trainerReply) {
        this.trainerReply = trainerReply;
        this.status = Status.DECLINED;
    }

    public String getId() { return id; }
    public String getSessionId() { return sessionId; }
    public String getMemberId() { return memberId; }
    public String getTrainerId() { return trainerId; }
    public LocalDate getRequestedDate() { return requestedDate; }
    public LocalTime getRequestedTime() { return requestedTime; }
    public String getReason() { return reason; }
    public String getTrainerReply() { return trainerReply; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Status getStatus() { return status; }
}