
package com.insurance.model;

import com.insurance.interfaces.Reportable;

public abstract class Policy implements Reportable {

    private final String policyNumber;
    private final PolicyHolder holder;
    private PolicyStatus status;

    public Policy(
            String policyNumber,
            PolicyHolder holder,
            PolicyStatus status
    ) {

        if (policyNumber == null ||
                !policyNumber.matches("POL-[0-9]{4}")) {
            throw new IllegalArgumentException("Invalid policy number.");
        }

        if (holder == null) {
            throw new IllegalArgumentException("Policy holder is required.");
        }

        if (status == null) {
            throw new IllegalArgumentException("Policy status is required.");
        }

        this.policyNumber = policyNumber;
        this.holder = holder;
        this.status = status;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public PolicyHolder getHolder() {
        return holder;
    }

    public PolicyStatus getStatus() {
        return status;
    }

    public void setStatus(PolicyStatus status) {

        if (status == null) {
            throw new IllegalArgumentException("Invalid status.");
        }

        this.status = status;
    }

    public abstract double calculatePremium();

    @Override
    public void generateReport() {

        System.out.printf(
                "%-12s %-15s %-12s %-12.2f%n",
                policyNumber,
                holder.getName(),
                status,
                calculatePremium()
        );
    }
}
