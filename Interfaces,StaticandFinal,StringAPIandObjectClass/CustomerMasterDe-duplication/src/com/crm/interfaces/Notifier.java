
package com.crm.interfaces;

public interface Notifier {

    void sendNotification(String recipient, String message);

    // Default method
    default void notificationStatus() {

        System.out.println("Notification service is ready.");
    }

    // Static helper method
    static boolean isValidRecipient(String recipient) {

        return recipient != null && !recipient.isBlank();
    }
}
