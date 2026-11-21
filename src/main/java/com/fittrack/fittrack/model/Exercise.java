package com.fittrack.fittrack.model;

import java.io.Serializable;

public class Exercise implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int sets;
    private String reps;
    private int restSeconds;
    private double loadKg;

    public Exercise(String name, int sets, String reps, int restSeconds, double loadKg) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.restSeconds = restSeconds;
        this.loadKg = loadKg;
    }

    public String getName() { return name; }
    public int getSets() { return sets; }
    public String getReps() { return reps; }
    public int getRestSeconds() { return restSeconds; }
    public double getLoadKg() { return loadKg; }
}