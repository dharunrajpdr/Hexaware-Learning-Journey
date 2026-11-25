
package com.insurance;

import java.util.List;

import com.insurance.model.Applicant;
import com.insurance.service.PremiumService;

public class Main {

    public static void main(String[] args) {

        var service = new PremiumService();

        // Eight applicants
        var applicants = List.of(

            new Applicant(101, "Arun", 25, 70, 1.75, false, 10),
            new Applicant(102, "Bala", 35, 85, 1.80, true, 15),
            new Applicant(103, "Charan", 50, 90, 1.70, false, 20),
            new Applicant(104, "Deepak", 28, 50, 1.65, false, 25),
            new Applicant(105, "Ezhil", 66, 75, 1.70, false, 10),
            new Applicant(106, "Fahad", 40, 100, 1.75, true, 10),
            new Applicant(107, "Gokul", 45, 68, 1.70, false, 20),
            new Applicant(108, "Hari", 60, 75, 1.70, true, 10)

        );

        System.out.println("\n===== INSURANCE BATCH VALIDATION =====\n");

        // Labelled block
        validation:
        {
            for (var applicant : applicants) {

                if (!service.isValid(applicant)) {

                    System.out.println(
                        "Applicant " + applicant.name()
                        + " is INELIGIBLE."
                    );

                    System.out.println(
                        "Batch validation stopped!"
                    );

                    break validation;
                }

                System.out.println(
                    "Applicant " + applicant.name()
                    + " - Valid"
                );
            }
        }

        // Display all eight applicant decisions
        System.out.println("\n========== PREMIUM REPORT ==========\n");

        System.out.printf(
            "%-5s %-10s %-6s %-14s %-14s %-12s %-12s%n",
            "ID", "Name", "Age", "BMI", "BMI Category",
            "Loading", "Premium"
        );

        System.out.println(
            "--------------------------------------------------------------------------"
        );

        for (var applicant : applicants) {

            if (!service.isValid(applicant)) {

                System.out.printf(
                    "%-5d %-10s %-6d %-14s %-14s %-12s %-12s%n",
                    applicant.id(),
                    applicant.name(),
                    applicant.age(),
                    "N/A",
                    "Ineligible",
                    "N/A",
                    "N/A"
                );

                continue;
            }

            double bmi = service.calculateBMI(applicant);

            String category = service.getBMICategory(bmi);

            double loading =
                    service.getLoadingPercentage(bmi);

            if (applicant.smoker()) {
                loading += 20.0;
            }

            double premium =
                    service.calculateFinalPremium(applicant);

            System.out.printf(
                "%-5d %-10s %-6d %-14.2f %-14s %-12.2f %-12.2f%n",
                applicant.id(),
                applicant.name(),
                applicant.age(),
                bmi,
                category,
                loading,
                premium
            );
        }

        System.out.println(
            "\n==================================="
        );
    }
}
