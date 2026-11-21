package com.fittrack.fittrack.model;

import java.util.List;

public enum MembershipTier {
    BRONZE(1000.0, List.of("Gym access (6AM-10PM)", "Locker access")),
    SILVER(1800.0, List.of("24/7 gym access", "Group Training", "Locker access")),
    GOLD(2500.0, List.of("24/7 gym access", "Personal Trainer", "Group Training", "Sauna"));

    private final double monthlyPrice;
    private final List<String> inclusions;

    MembershipTier(double monthlyPrice, List<String> inclusions) {
        this.monthlyPrice = monthlyPrice;
        this.inclusions = inclusions;
    }

    public double getMonthlyPrice() { return monthlyPrice; }
    public List<String> getInclusions() { return inclusions; }
}