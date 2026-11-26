
package com.crm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.crm.model.Customer;
import com.crm.model.CustomerRecord;
import com.crm.model.ImportedCustomer;

import com.crm.interfaces.*;
import com.crm.utility.StringUtil;

public class Main {

    public static void main(String[] args) {

        // 1. Customer records from three channels

        var channel1 = List.of(
            new Customer("Arun", "arun@gmail.com", "9876543210"),
            new Customer("Bala", "bala@gmail.com", "9876543211"),
            new Customer("Charan", "charan@gmail.com", "9876543212")
        );

        var channel2 = List.of(
            new Customer("Arun Kumar", " ARUN@gmail.com ", "9876543213"),
            new Customer("Deepak", "deepak@gmail.com", "9876543214"),
            new Customer("Ezhil", "ezhil@gmail.com", "9876543215")
        );

        var channel3 = List.of(
            new Customer("Bala Kumar", "BALA@GMAIL.COM", "9876543216"),
            new Customer("Fahad", "fahad@gmail.com", "9876543217"),
            new Customer("Gokul", "gokul@gmail.com", "9876543218")
        );

        // 2. HashSet removes duplicate customers

        Set<Customer> customers = new HashSet<>();

        customers.addAll(channel1);
        customers.addAll(channel2);
        customers.addAll(channel3);

        System.out.println("\n===== CUSTOMER MASTER DE-DUPLICATION =====");

        System.out.println("Total records received: " +
                (channel1.size() + channel2.size() + channel3.size()));

        System.out.println("Unique customers: " + customers.size());

        System.out.println("\n===== UNIQUE CUSTOMER RECORDS =====");

        customers.stream()
                .sorted((a, b) -> a.getEmail().compareTo(b.getEmail()))
                .forEach(System.out::println);

        // 3. Record comparison

        System.out.println("\n===== RECORD COMPARISON =====");

        var record1 = new CustomerRecord(" ARUN@gmail.com ");
        var record2 = new CustomerRecord("arun@gmail.com");

        System.out.println("Record 1: " + record1);
        System.out.println("Record 2: " + record2);

        System.out.println(
                "Records equal: " + record1.equals(record2)
        );

        System.out.println(
                "Same hash code: " +
                (record1.hashCode() == record2.hashCode())
        );

        // 4. String API demonstrations

        System.out.println("\n===== STRING API =====");

        String fullName = "  Arun Raj Kumar  ";

        System.out.println(
                "Clean name: " + StringUtil.clean(fullName)
        );

        String[] names = StringUtil.splitName(fullName);

        System.out.println("First name: " + names[0]);
        System.out.println("Last name: " + names[names.length - 1]);

        System.out.println(
                "Normalized email: " +
                StringUtil.normalize(" ARUN@GMAIL.COM ")
        );

        System.out.println(
                "Masked phone: " +
                StringUtil.maskPhone("9876543210")
        );

        // 5. String immutability

        System.out.println("\n===== STRING IMMUTABILITY =====");

        StringUtil.demonstrateImmutability();

        // 6. String constant pool

        System.out.println("\n===== STRING CONSTANT POOL =====");

        StringUtil.demonstrateStringPool();

        // 7. Interface default and static methods

        System.out.println("\n===== NOTIFICATION SERVICES =====");

        Notifier email = new EmailNotifier();

        Notifier sms = new SmsNotifier();

        email.notificationStatus();

        email.sendNotification(
                "customer@gmail.com",
                "Your customer profile is registered."
        );

        sms.sendNotification(
                "9876543210",
                "Your CRM registration is successful."
        );

        // 8. Marker interface

        System.out.println("\n===== MARKER INTERFACE =====");

        var imported = new ImportedCustomer("Website");

        if (imported instanceof Auditable) {

            System.out.println(
                    "Imported customer is eligible for audit."
            );

            System.out.println(
                    "Source: " + imported.getSource()
            );
        }

        // 9. Functional interface with lambda

        System.out.println("\n===== FUNCTIONAL INTERFACE =====");

        CustomerProcessor processor = name ->
                System.out.println(
                        "Processing customer: " + name
                );

        processor.process("Arun");
        processor.process("Bala");

        System.out.println("\n===== PROCESS COMPLETED =====");
    }
}
