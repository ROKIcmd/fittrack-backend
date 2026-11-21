package com.fittrack.fittrack.model;

import java.io.Serializable;
import java.time.LocalDate;

public class BodyMetric implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String memberId;
    private String trainerId;
    private LocalDate recordedDate;
    private double weightKg;
    private double bodyFatPercent;
    private double muscleMassKg;
    private double bmi;
    private String trainerNote;

    public BodyMetric(String id, String memberId, String trainerId, LocalDate recordedDate,
                      double weightKg, double bodyFatPercent, double muscleMassKg,
                      double heightMeters, String trainerNote) {
        this.id = id;
        this.memberId = memberId;
        this.trainerId = trainerId;
        this.recordedDate = recordedDate;
        this.weightKg = weightKg;
        this.bodyFatPercent = bodyFatPercent;
        this.muscleMassKg = muscleMassKg;
        this.bmi = calculateBmi(weightKg, heightMeters);
        this.trainerNote = trainerNote;
    }

    private double calculateBmi(double weightKg, double heightMeters) {
        if (heightMeters <= 0) return 0.0;
        return Math.round((weightKg / (heightMeters * heightMeters)) * 10.0) / 10.0;
    }

    public String getId() { return id; }
    public String getMemberId() { return memberId; }
    public String getTrainerId() { return trainerId; }
    public LocalDate getRecordedDate() { return recordedDate; }
    public double getWeightKg() { return weightKg; }
    public double getBodyFatPercent() { return bodyFatPercent; }
    public double getMuscleMassKg() { return muscleMassKg; }
    public double getBmi() { return bmi; }
    public String getTrainerNote() { return trainerNote; }
}