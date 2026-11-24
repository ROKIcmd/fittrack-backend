package com.fittrack.fittrack.config;

import com.fittrack.fittrack.model.*;
import com.fittrack.fittrack.repository.DataStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final String MEMBER_ID = "M001";
    private static final String TRAINER_ID = "T001";

    private final DataStore store = DataStore.getInstance();

    @Override
    public void run(String... args) {
        seedUsers();          // always upserts, so trainer assignment stays correct
        seedMembership();
        seedBilling();
        seedBodyMetrics();
        seedSessions();
        seedNotifications();
    }

    private void seedUsers() {
        Member member = new Member(MEMBER_ID, "Juan", "Dela Cruz", "juandelacruz@gmail.com", "password123",
                LocalDate.of(1998, 5, 12), "Male", "Muscle gain", 1.75, 80.0);
        member.setAssignedTrainerId(TRAINER_ID);
        store.users().save(member);

        Trainer trainer = new Trainer(TRAINER_ID, "John", "Smith", "johnsmith@fittrack.com", "password123",
                "Bachelor in PE", "Strength training", 5, "Consistency is the key.");
        store.users().save(trainer);
    }

    private void seedMembership() {
        if (store.memberships().findById("MS001").isPresent()) return;
        // Monthly cycle is 30 days. Starting 27 days ago leaves 3 days remaining, matching the mockup.
        LocalDate start = LocalDate.now().minusDays(27);
        store.memberships().save(
                new Membership("MS001", MEMBER_ID, MembershipTier.GOLD, BillingCycle.MONTHLY, start));
    }

    private void seedBilling() {
        if (store.billing().findById("B001").isPresent()) return;
        for (int i = 1; i <= 5; i++) {
            BillingRecord record = new BillingRecord("B00" + i, MEMBER_ID, MembershipTier.GOLD,
                    BillingCycle.MONTHLY, String.format("12345678901%03d", i));
            if (i > 1) record.approve();   // newest stays PENDING, matching the mockup
            store.billing().save(record);
        }
    }

    private void seedBodyMetrics() {
        if (store.metrics().findById("BM00").isPresent()) return;
        int points = 15;
        for (int i = 0; i < points; i++) {
            double t = i / (double) (points - 1);          // 0.0 = oldest, 1.0 = latest
            LocalDate date = LocalDate.now().minusDays((points - 1 - i) * 2L);
            double weight = round1(80.0 + t * (78.4 - 80.0));
            double bodyFat = round1(16.4 + t * (15.2 - 16.4));
            double muscle = round1(60.8 + t * (63.1 - 60.8));
            String note = (i == points - 1) ? "Slight water drop, strength up" : null;
            store.metrics().save(new BodyMetric(String.format("BM%02d", i), MEMBER_ID, TRAINER_ID,
                    date, weight, bodyFat, muscle, 1.75, note));
        }
    }

    private void seedSessions() {
        if (store.sessions().findById("S001").isPresent()) return;
        LocalDate today = LocalDate.now();

        // Upcoming (dashboard schedule widget)
        addSession("S001", today.plusDays(1), 60, TrainingSession.Status.SCHEDULED, "Legs", 0, 6);

        // Recent workout log: every status badge appears at least once
        addSession("S002", today.minusDays(2),  45, TrainingSession.Status.COMPLETED,   "Push", 6, 6);
        addSession("S003", today.minusDays(4),  45, TrainingSession.Status.COMPLETED,   "Push", 6, 6);
        addSession("S004", today.minusDays(6),  45, TrainingSession.Status.RESCHEDULED, "Push", 6, 6);
        addSession("S005", today.minusDays(7),  45, TrainingSession.Status.COMPLETED,   "Push", 6, 6);
        addSession("S006", today.minusDays(9),  45, TrainingSession.Status.NO_SHOW,     "Push", 0, 6);
        addSession("S007", today.minusDays(11), 45, TrainingSession.Status.COMPLETED,   "Push", 6, 6);
    }

    private void seedNotifications() {
        if (store.notifications().findById("N001").isPresent()) return;
        LocalDateTime now = LocalDateTime.now();

        store.notifications().save(new Notification("N001", MEMBER_ID, Notification.Type.MEMBERSHIP_RENEWED,
                "Membership Renewal Approved", "Your Gold membership was renewed.", now.minusDays(6)));
        store.notifications().save(new Notification("N002", MEMBER_ID, Notification.Type.METRICS_UPDATED,
                "Trainer Updated Body Metrics", "John updated your body metrics.", now.minusDays(8)));
        store.notifications().save(new Notification("N003", MEMBER_ID, Notification.Type.RESCHEDULE_DECLINED,
                "Reschedule Declined", "Your reschedule request was declined.", now.minusDays(9)));
        store.notifications().save(new Notification("N004", MEMBER_ID, Notification.Type.RESCHEDULE_APPROVED,
                "Reschedule Approved", "Your session was moved.", now.minusDays(11)));
    }

    private void addSession(String id, LocalDate date, int durationMinutes, TrainingSession.Status status,
                            String routineCategory, int exercisesDone, int totalExercises) {
        store.sessions().save(new TrainingSession(id, MEMBER_ID, TRAINER_ID, "Upper body push", date,
                LocalTime.of(9, 0), durationMinutes, status, routineCategory,
                exercisesDone, totalExercises, 40.0));
    }

    private static double round1(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}