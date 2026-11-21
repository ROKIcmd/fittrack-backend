package com.fittrack.fittrack.model;

import java.io.Serializable;
import java.time.LocalDate;

public class BillingRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String memberId;
    private MembershipTier tier;
    private BillingCycle cycle;
    private double amount;
    private String referenceNumber;
    private LocalDate billingDate;
    private PaymentStatus status;

    public BillingRecord(String id, String memberId, MembershipTier tier, BillingCycle cycle, String referenceNumber) {
        this.id = id;
        this.memberId = memberId;
        this.tier = tier;
        this.cycle = cycle;
        this.amount = cycle.calculatePrice(tier);
        this.referenceNumber = referenceNumber;
        this.billingDate = LocalDate.now();
        this.status = PaymentStatus.PENDING;
    }

    public void approve() { this.status = PaymentStatus.APPROVED; }
    public void decline() { this.status = PaymentStatus.DECLINED; }

    public String getId() { return id; }
    public String getMemberId() { return memberId; }
    public MembershipTier getTier() { return tier; }
    public BillingCycle getCycle() { return cycle; }
    public double getAmount() { return amount; }
    public String getReferenceNumber() { return referenceNumber; }
    public LocalDate getBillingDate() { return billingDate; }
    public PaymentStatus getStatus() { return status; }
}