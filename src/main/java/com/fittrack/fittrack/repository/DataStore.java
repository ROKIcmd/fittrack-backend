package com.fittrack.fittrack.repository;

import com.fittrack.fittrack.model.*;

public class DataStore {
    private static volatile DataStore instance;

    private final UserRepository users = new UserRepository();
    private final PlanRepository plans = new PlanRepository();
    private final Repository<Membership> memberships = new FileRepository<>("data/memberships.dat", Membership::getId);
    private final Repository<BillingRecord> billing = new FileRepository<>("data/billing.dat", BillingRecord::getId);
    private final Repository<TrainingSession> sessions = new FileRepository<>("data/sessions.dat", TrainingSession::getId);
    private final Repository<RescheduleRequest> reschedules = new FileRepository<>("data/reschedules.dat", RescheduleRequest::getId);
    private final Repository<BodyMetric> metrics = new FileRepository<>("data/metrics.dat", BodyMetric::getId);

    private DataStore() {}

    public static DataStore getInstance() {
        if (instance == null) {
            synchronized (DataStore.class) {
                if (instance == null) {
                    instance = new DataStore();
                }
            }
        }
        return instance;
    }

    public UserRepository users() { return users; }
    public PlanRepository plans() { return plans; }
    public Repository<Membership> memberships() { return memberships; }
    public Repository<BillingRecord> billing() { return billing; }
    public Repository<TrainingSession> sessions() { return sessions; }
    public Repository<RescheduleRequest> reschedules() { return reschedules; }
    public Repository<BodyMetric> metrics() { return metrics; }
}