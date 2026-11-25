
package com.bank.service;

import com.bank.model.Applicant;

public class LoanEligibilityService {

    private static final int MAX_EXISTING_LOANS = 2;

    // Credit score bands using sealed interface
    private sealed interface CreditBand
            permits Excellent, Good, Fair, Poor {
    }

    private record Excellent() implements CreditBand {}
    private record Good() implements CreditBand {}
    private record Fair() implements CreditBand {}
    private record Poor() implements CreditBand {}

    // Result containing interest rate and loan product
    private record LoanOffer(double interestRate, String product) {}

    // Rule 1: Validate age
    private boolean checkAge(Applicant applicant) {
        return applicant.age() >= 21 && applicant.age() <= 60;
    }

    // Rule 2: Income-to-EMI ratio
    private boolean checkIncomeRatio(Applicant applicant) {
        if (applicant.monthlyIncome() <= 0) {
            return false;
        }

        if (applicant.monthlyEmi() < 0) {
            return false;
        }

        return applicant.monthlyIncome() >=
                applicant.monthlyEmi() * 3;
    }

    // Rule 3: Credit score classification
    private CreditBand getCreditBand(int score) {

        if (score >= 750 && score <= 900) {
            return new Excellent();
        } else if (score >= 700) {
            return new Good();
        } else if (score >= 650) {
            return new Fair();
        } else {
            return new Poor();
        }
    }

    // Enhanced switch expression with pattern matching
    private LoanOffer getLoanOffer(CreditBand band) {

        return switch (band) {
            case Excellent e -> new LoanOffer(8.5, "Premium Loan");
            case Good g      -> new LoanOffer(10.0, "Standard Loan");
            case Fair f      -> new LoanOffer(12.5, "Basic Loan");
            case Poor p      -> new LoanOffer(0.0, "Not Available");
        };
    }

    // Rule 4: Employment type
    private boolean checkEmployment(Applicant applicant) {

        String employment = applicant.employmentType();

        if (employment.equalsIgnoreCase("Salaried")) {
            return true;
        } else if (employment.equalsIgnoreCase("Self-Employed")) {
            if (applicant.monthlyIncome() >= 25000) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Rule 5: Existing loan limit
    private boolean checkExistingLoans(Applicant applicant) {
        return applicant.existingLoans() <= MAX_EXISTING_LOANS;
    }

    // Validate invalid records
    public boolean isValid(Applicant applicant) {

        if (applicant.age() < 0 ||
                applicant.monthlyIncome() < 0 ||
                applicant.monthlyEmi() < 0 ||
                applicant.existingLoans() < 0 ||
                applicant.creditScore() < 300 ||
                applicant.creditScore() > 900 ||
                applicant.name() == null ||
                applicant.employmentType() == null) {
            return false;
        }

        return true;
    }

    // Rule 6: Final eligibility decision
    public String evaluate(Applicant applicant) {

        if (!checkAge(applicant)) {
            return "Rejected - Age";
        } else if (!checkIncomeRatio(applicant)) {
            return "Rejected - Income";
        } else if (!checkEmployment(applicant)) {
            return "Rejected - Employment";
        } else if (!checkExistingLoans(applicant)) {
            return "Rejected - Existing Loans";
        } else {

            CreditBand band = getCreditBand(applicant.creditScore());

            if (band instanceof Poor) {
                return "Rejected - Credit Score";
            } else {
                return "Approved";
            }
        }
    }

    public LoanOfferDetails getOffer(int score) {

        if (score < 300 || score > 900) {
            return new LoanOfferDetails(0, "Not Available");
        }

        CreditBand band = getCreditBand(score);
        LoanOffer offer = getLoanOffer(band);

        return new LoanOfferDetails(
                offer.interestRate(),
                offer.product()
        );
    }

    public record LoanOfferDetails(
            double interestRate,
            String product
    ) {}
}
