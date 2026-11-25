
package com.insurance;

import com.insurance.model.*;
import com.insurance.service.CollectionReport;

public class Main {

    public static void main(String[] args) {

        // Create policy holders
        var holder1 = new PolicyHolder("Arun", "9876543210");
        var holder2 = new PolicyHolder("Bala", "9876543211");
        var holder3 = new PolicyHolder("Charan", "9876543212");
        var holder4 = new PolicyHolder("Deepak", "9876543213");
        var holder5 = new PolicyHolder("Ezhil", "9876543214");
        var holder6 = new PolicyHolder("Fahad", "9876543215");
        var holder7 = new PolicyHolder("Gokul", "9876543216");
        var holder8 = new PolicyHolder("Hari", "9876543217");

        // Store different policy objects in one parent-class array
        Policy[] policies = {

            new MotorPolicy(
                "POL-1001", holder1, PolicyStatus.ACTIVE,
                500000, 2.0
            ),

            new MotorPolicy(
                "POL-1002", holder2, PolicyStatus.ACTIVE,
                800000, 1.5
            ),

            new HealthPolicy(
                "POL-1003", holder3, PolicyStatus.ACTIVE,
                500000, 35
            ),

            new HealthPolicy(
                "POL-1004", holder4, PolicyStatus.DRAFT,
                800000, 50
            ),

            new TravelPolicy(
                "POL-1005", holder5, PolicyStatus.ACTIVE,
                10, 100
            ),

            new TravelPolicy(
                "POL-1006", holder6, PolicyStatus.LAPSED,
                15, 150
            ),

            new HomePolicy(
                "POL-1007", holder7, PolicyStatus.ACTIVE,
                5000000, 0.10
            ),

            new HomePolicy(
                "POL-1008", holder8, PolicyStatus.ACTIVE,
                8000000, 0.15
            )
        };

        // Runtime polymorphism
        System.out.println("\n========== INSURANCE POLICY REPORT ==========\n");

        System.out.printf(
            "%-12s %-15s %-12s %-15s%n",
            "Policy No", "Holder", "Status", "Premium (₹)"
        );

        System.out.println(
            "--------------------------------------------------------"
        );

        for (Policy policy : policies) {
            policy.generateReport();
        }

        // Display collection reports
        var report = new CollectionReport();

        report.displayRegularCollections();

        report.displayJaggedCollections();

        System.out.println("\n========== REPORT COMPLETED ==========");
    }
}
