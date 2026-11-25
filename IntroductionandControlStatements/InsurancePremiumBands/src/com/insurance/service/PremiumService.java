
package com.insurance.service;

import com.insurance.model.Applicant;

public class PremiumService {

    // Credit-independent premium base in INR
    private static final double BASE_PREMIUM = 10000.0;

    // BMI calculation
    public double calculateBMI(Applicant applicant) {

        return applicant.weight() /
                (applicant.height() * applicant.height());
    }

    // BMI classification and loading using nested if
    public double getLoadingPercentage(double bmi) {

        if (bmi < 18.5) {
            if (bmi < 16) {
                return 20.0;
            } else {
                return 5.0;
            }
        } else if (bmi < 25) {
            return 0.0;
        } else if (bmi < 30) {
            if (bmi < 27.5) {
                return 5.0;
            } else {
                return 10.0;
            }
        } else {
            if (bmi < 35) {
                return 15.0;
            } else {
                return 25.0;
            }
        }
    }

    // BMI category
    public String getBMICategory(double bmi) {

        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    // Sealed interface for age bands
    private sealed interface AgeBand
            permits Young, Adult, Senior, Elderly {
    }

    private record Young() implements AgeBand {}
    private record Adult() implements AgeBand {}
    private record Senior() implements AgeBand {}
    private record Elderly() implements AgeBand {}

    // Classify age
    private AgeBand getAgeBand(int age) {

        if (age >= 18 && age <= 30) {
            return new Young();
        } else if (age <= 45) {
            return new Adult();
        } else if (age <= 60) {
            return new Senior();
        } else {
            return new Elderly();
        }
    }

    // Switch expression with pattern matching and guards
    public double getBasePremium(int age, int term) {

        AgeBand band = getAgeBand(age);

        return switch (band) {

            case Young y when term <= 10 -> 8000.0;
            case Young y when term <= 20 -> 10000.0;
            case Young y -> 12000.0;

            case Adult a when term <= 10 -> 10000.0;
            case Adult a when term <= 20 -> 13000.0;
            case Adult a -> 15000.0;

            case Senior s when term <= 10 -> 15000.0;
            case Senior s when term <= 20 -> 18000.0;
            case Senior s -> 22000.0;

            case Elderly e -> 0.0;
        };
    }

    // Validate applicant
    public boolean isValid(Applicant applicant) {

        return applicant.age() >= 18 &&
                applicant.age() <= 65 &&
                applicant.weight() > 0 &&
                applicant.height() > 0 &&
                applicant.policyTerm() >= 5 &&
                applicant.policyTerm() <= 30;
    }

    // Final premium calculation
    public double calculateFinalPremium(Applicant applicant) {

        double bmi = calculateBMI(applicant);

        double loading = getLoadingPercentage(bmi);

        double basePremium = getBasePremium(
                applicant.age(),
                applicant.policyTerm()
        );

        double smokerLoading = applicant.smoker() ? 20.0 : 0.0;

        // Arithmetic and logical operations
        double totalLoading = loading + smokerLoading;

        boolean eligible =
                applicant.age() >= 18 &&
                applicant.age() <= 65 &&
                applicant.weight() > 0 &&
                applicant.height() > 0 &&
                applicant.policyTerm() >= 5 &&
                applicant.policyTerm() <= 30;

        if (eligible && basePremium > 0) {

            return basePremium +
                    (basePremium * totalLoading / 100.0);
        }

        return 0.0;
    }
}
