
package com.servicedesk;

import com.servicedesk.model.Ticket;

import java.time.LocalDateTime;
import java.util.*;

public class Main {

    // Sort by priority descending, creation time ascending,
    // and ticket number ascending.
    private static final Comparator<Ticket> TICKET_COMPARATOR =
            Comparator.comparing(
                    Ticket::getPriority,
                    Comparator.reverseOrder()
            )
            .thenComparing(Ticket::getCreatedAt)
            .thenComparing(Ticket::getTicketNumber);

    public static void main(String[] args) {

        List<Ticket> tickets = createTestData();

        System.out.println("==============================================");
        System.out.println("       SUPPORT TICKET QUEUE SYSTEM");
        System.out.println("==============================================");

        System.out.println("\nTOTAL TICKETS RECEIVED: " + tickets.size());

        // TASK 1
        LinkedHashSet<Ticket> uniqueTickets =
                removeDuplicateTickets(tickets);

        // TASK 2
        List<Ticket> sortedTickets =
                sortTickets(uniqueTickets);

        // TASK 3
        processPriorityQueue(uniqueTickets);

        // TASK 4
        generateReports(uniqueTickets);

        // TASK 5
        removeResolvedTickets(uniqueTickets);
    }

    // ------------------------------------------------
    // 30 TICKET TEST DATA
    // ------------------------------------------------

    private static List<Ticket> createTestData() {

        List<Ticket> tickets = new ArrayList<>();

        tickets.add(new Ticket("T001", "Arun", "Login issue",
                "Account", 5, time(9, 0), false));

        tickets.add(new Ticket("T002", "Bala", "Payment failed",
                "Payment", 4, time(9, 10), false));

        tickets.add(new Ticket("T003", "Charan", "Unable to upload file",
                "Technical", 3, time(9, 15), false));

        tickets.add(new Ticket("T004", "Deepak", "Password reset",
                "Account", 2, time(9, 20), true));

        tickets.add(new Ticket("T005", "Ezhil", "Application crashing",
                "Technical", 5, time(9, 25), false));

        tickets.add(new Ticket("T006", "Fahad", "Refund request",
                "Payment", 3, time(9, 30), true));

        tickets.add(new Ticket("T007", "Gokul", "Network problem",
                "Network", 4, time(9, 35), false));

        tickets.add(new Ticket("T008", "Arun", "Email not working",
                "Technical", 2, time(9, 40), false));

        tickets.add(new Ticket("T009", "Bala", "Account locked",
                "Account", 5, time(9, 45), false));

        tickets.add(new Ticket("T010", "Charan", "Slow internet",
                "Network", 1, time(9, 50), true));

        tickets.add(new Ticket("T011", "Deepak", "Invoice missing",
                "Payment", 3, time(10, 0), false));

        tickets.add(new Ticket("T012", "Ezhil", "Software installation",
                "Technical", 4, time(10, 5), false));

        tickets.add(new Ticket("T013", "Fahad", "Profile update",
                "Account", 2, time(10, 10), true));

        tickets.add(new Ticket("T014", "Gokul", "Server unavailable",
                "Network", 5, time(10, 15), false));

        tickets.add(new Ticket("T015", "Arun", "Payment timeout",
                "Payment", 4, time(10, 20), false));

        // Duplicate of T001 - later ticket
        tickets.add(new Ticket("T016", "Arun", "Login issue",
                "Account", 3, time(10, 25), false));

        tickets.add(new Ticket("T017", "Bala", "Printer not working",
                "Technical", 2, time(10, 30), true));

        tickets.add(new Ticket("T018", "Charan", "Account locked",
                "Account", 4, time(10, 35), false));

        tickets.add(new Ticket("T019", "Deepak", "Network problem",
                "Network", 3, time(10, 40), false));

        tickets.add(new Ticket("T020", "Ezhil", "Refund request",
                "Payment", 5, time(10, 45), false));

        // Duplicate of T002, but earlier creation time
        tickets.add(new Ticket("T021", "Bala", "Payment failed",
                "Payment", 4, time(8, 55), false));

        tickets.add(new Ticket("T022", "Fahad", "Login issue",
                "Account", 3, time(10, 55), false));

        tickets.add(new Ticket("T023", "Gokul", "Application crashing",
                "Technical", 5, time(11, 0), false));

        tickets.add(new Ticket("T024", "Arun", "Slow internet",
                "Network", 2, time(11, 5), true));

        // Duplicate of T004
        tickets.add(new Ticket("T025", "Deepak", "Password reset",
                "Account", 4, time(11, 10), false));

        tickets.add(new Ticket("T026", "Bala", "Software installation",
                "Technical", 3, time(11, 15), false));

        tickets.add(new Ticket("T027", "Charan", "Refund request",
                "Payment", 2, time(11, 20), true));

        // Duplicate of T008
        tickets.add(new Ticket("T028", "Arun", "Email not working",
                "Technical", 5, time(11, 25), false));

        tickets.add(new Ticket("T029", "Fahad", "Server unavailable",
                "Network", 4, time(11, 30), false));

        tickets.add(new Ticket("T030", "Gokul", "Profile update",
                "Account", 1, time(11, 35), true));

        return tickets;
    }

    private static LocalDateTime time(int hour, int minute) {
        return LocalDateTime.of(2026, 11, 27, hour, minute);
    }

    // ------------------------------------------------
    // TASK 1: REMOVE DUPLICATES USING LINKEDHASHSET
    // ------------------------------------------------

    private static LinkedHashSet<Ticket> removeDuplicateTickets(
            List<Ticket> tickets) {

        System.out.println("\n\n========== TASK 1: DUPLICATE REMOVAL ==========");

        // Sort by creation time first, so the earliest
        // ticket for each user-summary pair is inserted first.
        List<Ticket> chronological = new ArrayList<>(tickets);

        chronological.sort(
                Comparator.comparing(Ticket::getCreatedAt)
                        .thenComparing(Ticket::getTicketNumber)
        );

        LinkedHashSet<Ticket> uniqueTickets =
                new LinkedHashSet<>(chronological);

        System.out.println("Original ticket count : " + tickets.size());
        System.out.println("Unique ticket count   : " + uniqueTickets.size());
        System.out.println("Duplicate count       : "
                + (tickets.size() - uniqueTickets.size()));

        System.out.println("\nUnique tickets:");

        for (Ticket ticket : uniqueTickets) {
            System.out.println(ticket);
        }

        return uniqueTickets;
    }

    // ------------------------------------------------
    // TASK 2: SORT USING COMPARATOR
    // ------------------------------------------------

    private static List<Ticket> sortTickets(
            Set<Ticket> tickets) {

        System.out.println("\n\n========== TASK 2: SORTED TICKETS ==========");

        List<Ticket> sortedTickets = new ArrayList<>(tickets);

        sortedTickets.sort(TICKET_COMPARATOR);

        System.out.println("Priority: Descending");
        System.out.println("Creation time: Ascending");
        System.out.println("Ticket number: Ascending\n");

        for (Ticket ticket : sortedTickets) {
            System.out.println(ticket);
        }

        return sortedTickets;
    }

    // ------------------------------------------------
    // TASK 3: PRIORITY QUEUE
    // ------------------------------------------------

    private static void processPriorityQueue(Set<Ticket> tickets) {

        System.out.println("\n\n========== TASK 3: PRIORITY QUEUE ==========");

        PriorityQueue<Ticket> workQueue =
                new PriorityQueue<>(TICKET_COMPARATOR);

        workQueue.addAll(tickets);

        System.out.println("Serving tickets by priority:\n");

        int position = 1;

        while (!workQueue.isEmpty()) {

            Ticket ticket = workQueue.poll();

            System.out.println(
                    position + ". " + ticket.getTicketNumber()
                    + " | Priority: " + ticket.getPriority()
                    + " | User: " + ticket.getUser()
                    + " | Summary: " + ticket.getSummary()
            );

            position++;
        }
    }

    // ------------------------------------------------
    // TASK 4: CATEGORY AND USER REPORTS
    // ------------------------------------------------

    private static void generateReports(Set<Ticket> tickets) {

        System.out.println("\n\n========== TASK 4: TICKET REPORTS ==========");

        // HashMap: category report
        Map<String, Integer> categoryReport = new HashMap<>();

        // TreeMap: user report
        Map<String, Integer> userReport = new TreeMap<>();

        for (Ticket ticket : tickets) {

            categoryReport.put(
                    ticket.getCategory(),
                    categoryReport.getOrDefault(
                            ticket.getCategory(), 0
                    ) + 1
            );

            userReport.put(
                    ticket.getUser(),
                    userReport.getOrDefault(
                            ticket.getUser(), 0
                    ) + 1
            );
        }

        System.out.println("\nTickets per Category (HashMap):");

        for (Map.Entry<String, Integer> entry :
                categoryReport.entrySet()) {

            System.out.println(
                    entry.getKey() + " : " + entry.getValue()
            );
        }

        System.out.println("\nTickets per User (TreeMap):");

        for (Map.Entry<String, Integer> entry :
                userReport.entrySet()) {

            System.out.println(
                    entry.getKey() + " : " + entry.getValue()
            );
        }

        System.out.println("\nMap iteration order:");
        System.out.println("HashMap : No guaranteed iteration order");
        System.out.println("TreeMap : Sorted by key in ascending order");
    }

    // ------------------------------------------------
    // TASK 5: REMOVE RESOLVED TICKETS USING ITERATOR
    // ------------------------------------------------

    private static void removeResolvedTickets(Set<Ticket> tickets) {

        System.out.println("\n\n========== TASK 5: REMOVE RESOLVED TICKETS ==========");

        int beforeRemoval = tickets.size();

        Iterator<Ticket> iterator = tickets.iterator();

        while (iterator.hasNext()) {

            Ticket ticket = iterator.next();

            if (ticket.isResolved()) {
                System.out.println(
                        "Removing resolved ticket: "
                                + ticket.getTicketNumber()
                );

                iterator.remove();
            }
        }

        System.out.println("\nTickets before removal: " + beforeRemoval);
        System.out.println("Resolved tickets removed: "
                + (beforeRemoval - tickets.size()));
        System.out.println("Tickets remaining: " + tickets.size());

        System.out.println("\nRemaining open tickets:");

        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }
}
