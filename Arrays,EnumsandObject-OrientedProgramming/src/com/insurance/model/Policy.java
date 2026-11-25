
package com.insurance.model;

import com.insurance.interfaces.Reportable;

public abstract class Policy implements Reportable {

    private final String policyNumber;

    private String holderName;

    private PolicyStatus status;

    public Policy(
            String policyNumber,
            String holderName,
            PolicyStatus status
    ) {

        if (policyNumber == null ||
                !policyNumber.matches("POL-[0-9]{4}")) {

            throw new IllegalArgumentException(
                    "Invalid policy number. Use POL-1234 format."
            );
        }

        if (holderName == null || holderName.isBlank()) {
            throw new IllegalArgumentException(
                    "Policy holder name cannot be empty."
            );
        }

        if (status == null) {
            throw new IllegalArgumentException(
                    "Policy status cannot be null."
            );
        }

        this.policyNumber = policyNumber;
        this.holderName = holderName;
        this.status = status;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public PolicyStatus getStatus() {
        return status;
    }

    public void setStatus(PolicyStatus status) {

        if (status == null) {
            throw new IllegalArgumentException(
                    "Invalid policy status."
            );
        }

        this.status = status;
    }

    public abstract double calculatePremium();

    @Override
    public void generateReport() {

        System.out.printf(
                "%-12s %-12s %-12s %-12.2f%n",
                policyNumber,
                holderName,
                status,
                calculatePremium()
        );
    }
}
