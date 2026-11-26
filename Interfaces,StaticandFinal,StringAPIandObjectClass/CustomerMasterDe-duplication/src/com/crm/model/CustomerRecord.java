
package com.crm.model;

public record CustomerRecord(String email) {

    public CustomerRecord {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }

        email = email.trim().toLowerCase();
    }
}
