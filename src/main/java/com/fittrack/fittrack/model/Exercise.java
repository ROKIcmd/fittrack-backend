package com.fittrack.fittrack.model;

import java.io.Serializable;

public class Exercise implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String dayLabel;
    private String routineCategory;
    private int sets;
    private String reps;
    private int restSeconds;
    private double loadKg;

    public Exercise(String name, String dayLabel, String routineCategory, int sets, String reps, int restSeconds, double loadKg) {
        this.name = name;
        this.dayLabel = dayLabel;
        this.routineCategory = routineCategory;
        this.sets = sets;
        this.reps = reps;
        this.restSeconds = restSeconds;
        this.loadKg = loadKg;
    }

    public String getName() {
        return name;
    }

    public String getDayLabel() {
        return dayLabel;
    }

    public String getRoutineCategory() {
        return routineCategory;
    }

    public int getSets() {
        return sets;
    }

    public String getReps() {
        return reps;
    }

    public int getRestSeconds() {
        return restSeconds;
    }

    public double getLoadKg() {
        return loadKg;
    }
}