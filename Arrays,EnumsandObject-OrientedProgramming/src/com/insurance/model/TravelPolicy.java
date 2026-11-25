
package com.insurance.model;

public class TravelPolicy extends Policy {

    private final int travelDays;
    private final double dailyPremium;

    public TravelPolicy(
            String policyNumber,
            PolicyHolder holder,
            PolicyStatus status,
            int travelDays,
            double dailyPremium
    ) {

        super(policyNumber, holder, status);

        if (travelDays <= 0 || dailyPremium <= 0) {
            throw new IllegalArgumentException("Invalid travel details.");
        }

        this.travelDays = travelDays;
        this.dailyPremium = dailyPremium;
    }

    @Override
    public double calculatePremium() {
        return travelDays * dailyPremium;
    }
}
