
package com.insurance.model;

public class MotorPolicy extends Policy {

    private final double vehicleValue;
    private final double riskPercentage;

    public MotorPolicy(
            String policyNumber,
            PolicyHolder holder,
            PolicyStatus status,
            double vehicleValue,
            double riskPercentage
    ) {

        super(policyNumber, holder, status);

        if (vehicleValue <= 0 || riskPercentage < 0) {
            throw new IllegalArgumentException("Invalid motor details.");
        }

        this.vehicleValue = vehicleValue;
        this.riskPercentage = riskPercentage;
    }

    @Override
    public double calculatePremium() {
        return vehicleValue * riskPercentage / 100;
    }
}
