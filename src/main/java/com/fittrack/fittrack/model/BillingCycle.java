package com.fittrack.fittrack.model;

public enum BillingCycle {
    WEEKLY(7, 0.30),
    MONTHLY(30, 1.0),
    ANNUALLY(365, 10.0); // 2 months free

    private final int days;
    private final double priceMultiplier;

    BillingCycle(int days, double priceMultiplier) {
        this.days = days;
        this.priceMultiplier = priceMultiplier;
    }

    public int getDays() { return days; }
    public double calculatePrice(MembershipTier tier) {
        return tier.getMonthlyPrice() * priceMultiplier;
    }
}