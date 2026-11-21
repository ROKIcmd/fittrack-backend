package com.fittrack.fittrack.model;

import java.time.LocalDate;

public class DietPlan extends FitnessPlan {
    private int dailyEnergyKcal;
    private int proteinGrams;
    private int carbsGrams;
    private int fatsGrams;
    private double dailyWaterLiters;
    private String dietaryNotes;

    public DietPlan(String id, String planName, String memberId, String trainerId, String goal,
                    int durationWeeks, LocalDate startDate, boolean draft, int dailyEnergyKcal,
                    int proteinGrams, int carbsGrams, int fatsGrams, double dailyWaterLiters, String dietaryNotes) {
        super(id, planName, memberId, trainerId, goal, durationWeeks, startDate, draft);
        this.dailyEnergyKcal = dailyEnergyKcal;
        this.proteinGrams = proteinGrams;
        this.carbsGrams = carbsGrams;
        this.fatsGrams = fatsGrams;
        this.dailyWaterLiters = dailyWaterLiters;
        this.dietaryNotes = dietaryNotes;
    }

    @Override
    public String getPlanType() { return "DIET"; }

    public int getDailyEnergyKcal() { return dailyEnergyKcal; }
    public void setDailyEnergyKcal(int dailyEnergyKcal) { this.dailyEnergyKcal = dailyEnergyKcal; }
    public int getProteinGrams() { return proteinGrams; }
    public void setProteinGrams(int proteinGrams) { this.proteinGrams = proteinGrams; }
    public int getCarbsGrams() { return carbsGrams; }
    public void setCarbsGrams(int carbsGrams) { this.carbsGrams = carbsGrams; }
    public int getFatsGrams() { return fatsGrams; }
    public void setFatsGrams(int fatsGrams) { this.fatsGrams = fatsGrams; }
    public double getDailyWaterLiters() { return dailyWaterLiters; }
    public void setDailyWaterLiters(double dailyWaterLiters) { this.dailyWaterLiters = dailyWaterLiters; }
    public String getDietaryNotes() { return dietaryNotes; }
    public void setDietaryNotes(String dietaryNotes) { this.dietaryNotes = dietaryNotes; }
}