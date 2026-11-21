package com.fittrack.fittrack.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkoutPlan extends FitnessPlan {
    private int daysPerWeek;
    private String coachingNotes;
    private final List<Exercise> exercises = new ArrayList<>();

    public WorkoutPlan(String id, String planName, String memberId, String trainerId, String goal,
                       int durationWeeks, LocalDate startDate, boolean draft, int daysPerWeek, String coachingNotes) {
        super(id, planName, memberId, trainerId, goal, durationWeeks, startDate, draft);
        this.daysPerWeek = daysPerWeek;
        this.coachingNotes = coachingNotes;
    }

    @Override
    public String getPlanType() { return "WORKOUT"; }

    public void addExercise(Exercise exercise) { exercises.add(exercise); }
    public List<Exercise> getExercises() { return Collections.unmodifiableList(exercises); }
    public int getDaysPerWeek() { return daysPerWeek; }
    public void setDaysPerWeek(int daysPerWeek) { this.daysPerWeek = daysPerWeek; }
    public String getCoachingNotes() { return coachingNotes; }
    public void setCoachingNotes(String coachingNotes) { this.coachingNotes = coachingNotes; }
}