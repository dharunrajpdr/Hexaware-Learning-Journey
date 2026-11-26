
package com.crm.interfaces;

public class SmsNotifier implements Notifier {

    @Override
    public void sendNotification(
            String recipient,
            String message
    ) {

        if (!Notifier.isValidRecipient(recipient)) {
            System.out.println("Invalid phone recipient.");
            return;
        }

        System.out.println(
                "SMS sent to " + recipient +
                " | Message: " + message
        );
    }
}
