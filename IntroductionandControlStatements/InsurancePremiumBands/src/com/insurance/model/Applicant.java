
package com.insurance.model;

public record Applicant(
        int id,
        String name,
        int age,
        double weight,
        double height,
        boolean smoker,
        int policyTerm
) {
}
