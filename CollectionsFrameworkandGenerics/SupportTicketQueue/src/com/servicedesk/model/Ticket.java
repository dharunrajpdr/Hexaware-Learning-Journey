
package com.servicedesk.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {

    private final String ticketNumber;
    private final String user;
    private final String summary;
    private final String category;
    private final Integer priority;
    private final LocalDateTime createdAt;
    private final boolean resolved;

    public Ticket(String ticketNumber, String user, String summary,
                  String category, Integer priority,
                  LocalDateTime createdAt, boolean resolved) {

        this.ticketNumber = ticketNumber;
        this.user = user;
        this.summary = summary;
        this.category = category;
        this.priority = priority;
        this.createdAt = createdAt;
        this.resolved = resolved;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getUser() {
        return user;
    }

    public String getSummary() {
        return summary;
    }

    public String getCategory() {
        return category;
    }

    public Integer getPriority() {
        return priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isResolved() {
        return resolved;
    }

    // Duplicate identity: same user and same summary
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Ticket other)) {
            return false;
        }

        return user.equalsIgnoreCase(other.user)
                && summary.equalsIgnoreCase(other.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                user.toLowerCase(),
                summary.toLowerCase()
        );
    }

    @Override
    public String toString() {
        return String.format(
                "%-5s | User: %-7s | Priority: %d | Category: %-8s | Created: %s | Status: %s | Summary: %s",
                ticketNumber,
                user,
                priority,
                category,
                createdAt,
                resolved ? "Resolved" : "Open",
                summary
        );
    }
}
