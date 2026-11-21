package com.fittrack.fittrack.model;

public class Trainer extends User {
    private String education;
    private String specialization;
    private int yearsOfExperience;
    private String shortMessage;

    public Trainer(String id, String firstName, String lastName, String email, String password,
                   String education, String specialization, int yearsOfExperience, String shortMessage) {
        super(id, firstName, lastName, email, password);
        this.education = education;
        this.specialization = specialization;
        this.yearsOfExperience = yearsOfExperience;
        this.shortMessage = shortMessage;
    }

    @Override
    public String getRole() { return "TRAINER"; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
    public String getShortMessage() { return shortMessage; }
    public void setShortMessage(String shortMessage) { this.shortMessage = shortMessage; }
}
