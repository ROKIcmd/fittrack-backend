package com.fittrack.fittrack.model;

import java.time.LocalDate;

public class Member extends User {
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String sex;
    private String primaryGoal;
    private double heightMeters;
    private double targetWeightKg;
    private String assignedTrainerId;

    public Member(String id, String firstName, String lastName, String email, String password,
                  LocalDate dateOfBirth, String sex, String primaryGoal, double heightMeters, double targetWeightKg) {
        super(id, firstName, lastName, email, password);
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.primaryGoal = primaryGoal;
        this.heightMeters = heightMeters;
        this.targetWeightKg = targetWeightKg;
    }

    @Override
    public String getRole() { return "MEMBER"; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getSex() { return sex; }
    public String getPrimaryGoal() { return primaryGoal; }
    public void setPrimaryGoal(String primaryGoal) { this.primaryGoal = primaryGoal; }
    public double getHeightMeters() { return heightMeters; }
    public void setHeightMeters(double heightMeters) { this.heightMeters = heightMeters; }
    public double getTargetWeightKg() { return targetWeightKg; }
    public void setTargetWeightKg(double targetWeightKg) { this.targetWeightKg = targetWeightKg; }
    public String getAssignedTrainerId() { return assignedTrainerId; }
    public void setAssignedTrainerId(String assignedTrainerId) { this.assignedTrainerId = assignedTrainerId; }
}