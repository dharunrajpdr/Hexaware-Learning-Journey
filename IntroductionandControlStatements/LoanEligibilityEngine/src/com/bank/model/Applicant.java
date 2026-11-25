
package com.bank.model;

public record Applicant(
        int id,
        String name,
        int age,
        double monthlyIncome,
        double monthlyEmi,
        int creditScore,
        String employmentType,
        int existingLoans
) {
  
}
