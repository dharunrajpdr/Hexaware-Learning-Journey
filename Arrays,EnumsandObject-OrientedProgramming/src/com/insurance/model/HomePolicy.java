
package com.insurance.model;

public class HomePolicy extends Policy {

    private final double propertyValue;
    private final double coverageRate;

    public HomePolicy(
            String policyNumber,
            PolicyHolder holder,
            PolicyStatus status,
            double propertyValue,
            double coverageRate
    ) {

        super(policyNumber, holder, status);

        if (propertyValue <= 0 || coverageRate <= 0) {
            throw new IllegalArgumentException("Invalid home details.");
        }

        this.propertyValue = propertyValue;
        this.coverageRate = coverageRate;
    }

    @Override
    public double calculatePremium() {
        return propertyValue * coverageRate / 100;
    }
}
