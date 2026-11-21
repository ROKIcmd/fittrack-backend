package com.fittrack.fittrack.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class TrainingSession implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Status { SCHEDULED, STARTED, COMPLETED, NO_SHOW, RESCHEDULED }

    private String id;
    private String memberId;
    private String trainerId;
    private String title; // e.g., "Upper body push"
    private LocalDate date;
    private LocalTime startTime;
    private int durationMinutes;
    private Status status;

    public TrainingSession(String id, String memberId, String trainerId, String title,
                           LocalDate date, LocalTime startTime, int durationMinutes) {
        this.id = id;
        this.memberId = memberId;
        this.trainerId = trainerId;
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.status = Status.SCHEDULED;
    }

    public void start() { this.status = Status.STARTED; }
    public void complete() { this.status = Status.COMPLETED; }
    public void markNoShow() { this.status = Status.NO_SHOW; }
    public void reschedule(LocalDate newDate, LocalTime newTime) {
        this.date = newDate;
        this.startTime = newTime;
        this.status = Status.RESCHEDULED;
    }

    public String getId() { return id; }
    public String getMemberId() { return memberId; }
    public String getTrainerId() { return trainerId; }
    public String getTitle() { return title; }
    public LocalDate getDate() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public int getDurationMinutes() { return durationMinutes; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}