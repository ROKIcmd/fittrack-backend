package com.fittrack.fittrack.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Membership implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String memberId;
    private MembershipTier tier;
    private BillingCycle cycle;
    private LocalDate startDate;
    private LocalDate renewalDate;
    private boolean active;

    public Membership(String id, String memberId, MembershipTier tier, BillingCycle cycle, LocalDate startDate) {
        this.id = id;
        this.memberId = memberId;
        this.tier = tier;
        this.cycle = cycle;
        this.startDate = startDate;
        this.renewalDate = startDate.plusDays(cycle.getDays());
        this.active = true;
    }

    public long getDaysRemaining() {
        long days = ChronoUnit.DAYS.between(LocalDate.now(), renewalDate);
        return Math.max(0, days);
    }

    public void renew(MembershipTier newTier, BillingCycle newCycle) {
        this.tier = newTier;
        this.cycle = newCycle;
        LocalDate base = renewalDate.isAfter(LocalDate.now()) ? renewalDate : LocalDate.now();
        this.renewalDate = base.plusDays(newCycle.getDays());
        this.active = true;
    }

    public String getId() { return id; }
    public String getMemberId() { return memberId; }
    public MembershipTier getTier() { return tier; }
    public BillingCycle getCycle() { return cycle; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getRenewalDate() { return renewalDate; }
    public boolean isActive() { return active && getDaysRemaining() > 0; }
    public void setActive(boolean active) { this.active = active; }
}