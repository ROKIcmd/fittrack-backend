package com.fittrack.fittrack.model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class FitnessPlan implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String planName;
    private String memberId;
    private String trainerId;
    private String goal;
    private int durationWeeks;
    private LocalDate startDate;
    private boolean draft;

    public FitnessPlan(String id, String planName, String memberId, String trainerId,
                       String goal, int durationWeeks, LocalDate startDate, boolean draft) {
        this.id = id;
        this.planName = planName;
        this.memberId = memberId;
        this.trainerId = trainerId;
        this.goal = goal;
        this.durationWeeks = durationWeeks;
        this.startDate = startDate;
        this.draft = draft;
    }

    public abstract String getPlanType();

    public void publish(String memberId, LocalDate startDate) {
        this.memberId = memberId;
        this.startDate = startDate;
        this.draft = false;
    }

    public String getId() { return id; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    public String getMemberId() { return memberId; }
    public String getTrainerId() { return trainerId; }
    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }
    public int getDurationWeeks() { return durationWeeks; }
    public void setDurationWeeks(int durationWeeks) { this.durationWeeks = durationWeeks; }
    public LocalDate getStartDate() { return startDate; }
    public boolean isDraft() { return draft; }
}