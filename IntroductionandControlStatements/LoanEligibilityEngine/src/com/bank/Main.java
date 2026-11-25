
package com.bank;

import java.util.List;

import com.bank.model.Applicant;
import com.bank.service.LoanEligibilityService;

public class Main {

    public static void main(String[] args) {

        var service = new LoanEligibilityService();

        // Ten applicants
        var applicants = List.of(
            new Applicant(101, "Arun", 25, 50000, 10000, 780, "Salaried", 1),
            new Applicant(102, "Bala", -5, 40000, 5000, 750, "Salaried", 0),
            new Applicant(103, "Charan", 30, 45000, 10000, 950, "Salaried", 1),
            new Applicant(104, "Deepak", 35, 60000, 15000, 800, "Salaried", 1),
            new Applicant(105, "Ezhil", 19, 30000, 5000, 720, "Salaried", 0),
            new Applicant(106, "Fahad", 40, 75000, 20000, 720, "Salaried", 2),
            new Applicant(107, "Gokul", 28, 20000, 5000, 760, "Self-Employed", 1),
            new Applicant(108, "Hari", 45, 50000, 10000, 670, "Salaried", 0),
            new Applicant(109, "Imran", 32, 80000, 10000, 820, "Salaried", 3),
            new Applicant(110, "John", 38, 60000, 10000, 620, "Salaried", 1)
        );

        int approvalCount = 0;
        final int DAILY_APPROVAL_LIMIT = 3;

        System.out.println("\n========== LOAN ELIGIBILITY ENGINE ==========\n");

        System.out.printf(
            "%-5s %-10s %-12s %-12s %-12s %-15s%n",
            "ID", "Name", "Age", "Credit", "Decision", "Loan Product"
        );

        System.out.println(
            "---------------------------------------------------------------------"
        );

        // Process applicants
        for (var applicant : applicants) {

            // Skip invalid records
            if (!service.isValid(applicant)) {

                System.out.printf(
                    "%-5d %-10s %-12d %-12d %-15s %-15s%n",
                    applicant.id(),
                    applicant.name(),
                    applicant.age(),
                    applicant.creditScore(),
                    "Invalid",
                    "N/A"
                );

                continue;
            }

            // Evaluate eligibility
            var decision = service.evaluate(applicant);

            var offer = service.getOffer(applicant.creditScore());

            System.out.printf(
                "%-5d %-10s %-12d %-12d %-15s %-15s%n",
                applicant.id(),
                applicant.name(),
                applicant.age(),
                applicant.creditScore(),
                decision,
                decision.equals("Approved") ? offer.product() : "N/A"
            );

            // Count approvals
            if (decision.equals("Approved")) {

                approvalCount++;

                // Stop after daily approval limit
                if (approvalCount >= DAILY_APPROVAL_LIMIT) {

                    System.out.println(
                        "\nDaily approval limit reached!"
                    );

                    break;
                }
            }
        }

        System.out.println(
            "\nTotal Approved: " + approvalCount
        );

        System.out.println(
            "=============================================="
        );
    }
}
