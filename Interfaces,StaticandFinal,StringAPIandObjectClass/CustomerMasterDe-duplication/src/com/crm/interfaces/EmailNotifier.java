
package com.crm.interfaces;

public class EmailNotifier implements Notifier {

    @Override
    public void sendNotification(
            String recipient,
            String message
    ) {

        if (!Notifier.isValidRecipient(recipient)) {
            System.out.println("Invalid email recipient.");
            return;
        }

        System.out.println(
                "Email sent to " + recipient +
                " | Message: " + message
        );
    }
}
