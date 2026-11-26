
package com.crm.model;

import java.util.Objects;

public class Customer {

    private final String name;
    private final String email;
    private final String phone;

    public Customer(String name, String email, String phone) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required.");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }

        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone is required.");
        }

        this.name = name.trim();
        this.email = normalizeEmail(email);
        this.phone = phone.trim();
    }

    private static String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    // Compare customers using normalized email
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Customer other)) {
            return false;
        }

        return email.equals(other.email);
    }

    // Generate hash code using normalized email
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    // Display customer information
    @Override
    public String toString() {

        return "Customer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
