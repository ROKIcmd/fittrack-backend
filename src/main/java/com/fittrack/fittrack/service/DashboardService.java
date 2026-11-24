package com.fittrack.fittrack.service;

import com.fittrack.fittrack.dto.DashboardResponse;
import com.fittrack.fittrack.dto.DashboardResponse.*;
import com.fittrack.fittrack.model.*;
import com.fittrack.fittrack.repository.DataStore;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class DashboardService {

    private final DataStore store = DataStore.getInstance();

    public Optional<DashboardResponse> build(String memberId) {
        Optional<User> found = store.users().findById(memberId);
        if (found.isEmpty() || !(found.get() instanceof Member member)) {
            return Optional.empty();
        }

        LocalDate today = LocalDate.now();
        Membership membership = latestMembership(memberId);

        return Optional.of(new DashboardResponse(
                member.getId(),
                member.getFirstName(),
                toMembershipCard(membership),
                buildPlans(membership),
                buildBilling(memberId),
                buildUpcoming(memberId, today),
                buildRecentWorkouts(memberId, today),
                buildProgress(memberId),
                buildBodyStats(memberId),
                buildNotifications(memberId),
                buildTrainer(member)
        ));
    }

    private Membership latestMembership(String memberId) {
        return store.memberships().findAll().stream()
                .filter(m -> memberId.equals(m.getMemberId()))
                .max(Comparator.comparing(Membership::getStartDate))
                .orElse(null);
    }

    private MembershipCard toMembershipCard(Membership m) {
        if (m == null) return null;
        int cycleDays = m.getCycle().getDays();
        long remaining = m.getDaysRemaining();
        int progress = (int) Math.round(100.0 * (cycleDays - remaining) / cycleDays);
        progress = Math.max(0, Math.min(100, progress));
        return new MembershipCard(
                m.getTier().name(),
                m.getCycle().name(),
                m.isActive(),
                remaining,
                m.getRenewalDate(),
                progress,
                m.getTier().getInclusions()
        );
    }

    private List<PlanOption> buildPlans(Membership current) {
        MembershipTier currentTier = current == null ? null : current.getTier();
        return java.util.Arrays.stream(MembershipTier.values())
                .map(t -> new PlanOption(t.name(), t.getMonthlyPrice(), t.getInclusions(), t == currentTier))
                .toList();
    }

    private List<BillingItem> buildBilling(String memberId) {
        return store.billing().findAll().stream()
                .filter(b -> memberId.equals(b.getMemberId()))
                .sorted(Comparator.comparing(BillingRecord::getBillingDate).reversed())
                .map(b -> new BillingItem(b.getBillingDate(), b.getReferenceNumber(),
                        b.getStatus().name(), b.getAmount()))
                .toList();
    }

    private UpcomingSession buildUpcoming(String memberId, LocalDate today) {
        return store.sessions().findAll().stream()
                .filter(s -> memberId.equals(s.getMemberId()))
                .filter(s -> s.getStatus() == TrainingSession.Status.SCHEDULED)
                .filter(s -> !s.getDate().isBefore(today))
                .min(Comparator.comparing(TrainingSession::getDate)
                        .thenComparing(TrainingSession::getStartTime))
                .map(s -> new UpcomingSession(s.getId(), s.getTitle(), s.getRoutineCategory(),
                        s.getDate(), s.getStartTime(), s.getDurationMinutes(), s.getStatus().name()))
                .orElse(null);
    }

    private List<WorkoutLogItem> buildRecentWorkouts(String memberId, LocalDate today) {
        return store.sessions().findAll().stream()
                .filter(s -> memberId.equals(s.getMemberId()))
                .filter(s -> s.getStatus() != TrainingSession.Status.SCHEDULED)
                .filter(s -> !s.getDate().isAfter(today))
                .sorted(Comparator.comparing(TrainingSession::getDate).reversed())
                .limit(6)
                .map(s -> new WorkoutLogItem(s.getId(), s.getTitle(), s.getDate(),
                        s.getDurationMinutes(), s.getLoadKg(), s.getExercisesCompleted(),
                        s.getTotalExercises(), s.getStatus().name()))
                .toList();
    }

    private List<BodyMetric> sortedMetrics(String memberId) {
        return store.metrics().findAll().stream()
                .filter(m -> memberId.equals(m.getMemberId()))
                .sorted(Comparator.comparing(BodyMetric::getRecordedDate))
                .toList();
    }

    private List<ProgressPoint> buildProgress(String memberId) {
        return sortedMetrics(memberId).stream()
                .map(m -> new ProgressPoint(m.getRecordedDate(), m.getMuscleMassKg()))
                .toList();
    }

    private BodyStats buildBodyStats(String memberId) {
        List<BodyMetric> metrics = sortedMetrics(memberId);
        if (metrics.isEmpty()) return null;
        BodyMetric latest = metrics.get(metrics.size() - 1);
        return new BodyStats(latest.getRecordedDate(), latest.getWeightKg(), latest.getBodyFatPercent(),
                latest.getMuscleMassKg(), latest.getBmi(), latest.getTrainerNote());
    }

    private List<NotificationItem> buildNotifications(String memberId) {
        return store.notifications().findByMemberId(memberId).stream()
                .map(n -> new NotificationItem(n.getType().name(), n.getTitle(), n.getMessage(),
                        n.getCreatedAt(), n.isRead()))
                .toList();
    }

    private TrainerCard buildTrainer(Member member) {
        String trainerId = member.getAssignedTrainerId();
        if (trainerId == null) return null;
        return store.users().findById(trainerId)
                .filter(u -> u instanceof Trainer)
                .map(u -> (Trainer) u)
                .map(t -> new TrainerCard(t.getId(), t.getFullName(), t.getEducation(),
                        t.getSpecialization(), t.getYearsOfExperience(), t.getShortMessage(),
                        store.users().findMembersByTrainer(t.getId()).size(),
                        null))   // no photo field yet, frontend uses a placeholder
                .orElse(null);
    }
}