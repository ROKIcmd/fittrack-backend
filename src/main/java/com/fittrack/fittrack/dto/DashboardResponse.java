package com.fittrack.fittrack.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record DashboardResponse(
        String memberId,
        String firstName,
        MembershipCard membership,
        List<PlanOption> plans,
        List<BillingItem> billingHistory,
        UpcomingSession upcomingSession,
        List<WorkoutLogItem> recentWorkouts,
        List<ProgressPoint> muscleGainProgress,
        BodyStats bodyStats,
        List<NotificationItem> notifications,
        TrainerCard trainer
) {

    /** Membership widget: current plan, status, and renewal countdown. */
    public record MembershipCard(
            String tier,
            String cycle,
            boolean active,
            long daysRemaining,
            LocalDate renewalDate,
            int progressPercent,
            List<String> inclusions
    ) {}

    /** One card in the Bronze / Silver / Gold list. */
    public record PlanOption(
            String tier,
            double monthlyPrice,
            List<String> inclusions,
            boolean current
    ) {}

    /** One  in the billing history table. */
    public record BillingItem(
            LocalDate date,
            String referenceNumber,
            String status,
            double amount
    ) {}

    public record UpcomingSession(
            String sessionId,
            String title,
            String routineCategory,
            LocalDate date,
            LocalTime startTime,
            int durationMinutes,
            String status
    ) {}

    /** One row in the recent workout log. */
    public record WorkoutLogItem(
            String sessionId,
            String title,
            LocalDate date,
            int durationMinutes,
            double loadKg,
            int exercisesCompleted,
            int totalExercises,
            String status
    ) {}

    /** One point on the muscle gain chart. */
    public record ProgressPoint(
            LocalDate date,
            double muscleMassKg
    ) {}

    /** The four stat tiles: weight, body fat, muscle mass, BMI. */
    public record BodyStats(
            LocalDate asOf,
            double weightKg,
            double bodyFatPercent,
            double muscleMassKg,
            double bmi,
            String trainerNote
    ) {}

    /** One item in the Updates list. */
    public record NotificationItem(
            String type,
            String title,
            String message,
            LocalDateTime createdAt,
            boolean read
    ) {}

    /** The personal trainer card (read-only). */
    public record TrainerCard(
            String trainerId,
            String fullName,
            String education,
            String specialization,
            int yearsOfExperience,
            String shortMessage,
            int currentClients,
            String photoUrl
    ) {}
}