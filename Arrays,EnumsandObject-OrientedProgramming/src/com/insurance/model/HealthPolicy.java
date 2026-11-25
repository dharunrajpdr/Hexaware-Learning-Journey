
package com.insurance.model;

public class HealthPolicy extends Policy {

    private final double coverageAmount;
    private final int age;

    public HealthPolicy(
            String policyNumber,
            PolicyHolder holder,
            PolicyStatus status,
            double coverageAmount,
            int age
    ) {

        super(policyNumber, holder, status);

        if (coverageAmount <= 0 || age < 18 || age > 100) {
            throw new IllegalArgumentException("Invalid health details.");
        }

        this.coverageAmount = coverageAmount;
        this.age = age;
    }

    @Override
    public double calculatePremium() {

        double rate = age <= 40 ? 2.0 : age <= 60 ? 3.0 : 5.0;

        return coverageAmount * rate / 100;
    }
}
