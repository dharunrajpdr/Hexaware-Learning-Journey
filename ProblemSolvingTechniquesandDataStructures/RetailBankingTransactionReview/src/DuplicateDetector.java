import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicateDetector {

    public static void detectDuplicates(Transaction[] transactions) {
        List<Transaction> sorted = new ArrayList<>();

        for (Transaction t : transactions) {
            sorted.add(t);
        }

        sorted.sort(Comparator.comparing(Transaction::getPostingDate));

        Map<String, List<Transaction>> seen = new HashMap<>();
        boolean found = false;

        for (Transaction current : sorted) {
            String key = current.getAccountNumber() + "|"
                    + current.getAmount() + "|"
                    + current.getMerchant();

            List<Transaction> previous = seen.computeIfAbsent(
                    key, k -> new ArrayList<>());

            for (Transaction earlier : previous) {
                long seconds = Math.abs(Duration.between(
                        earlier.getPostingDate(),
                        current.getPostingDate()).getSeconds());

                if (seconds <= 60) {
                    System.out.println("Possible duplicate detected:");
                    System.out.println("  Earlier: " + earlier);
                    System.out.println("  Current: " + current);
                    found = true;
                }
            }

            previous.add(current);
        }

        if (!found) {
            System.out.println("No duplicate transactions found.");
        }
    }

    public static void main(String[] args) {
        Transaction[] transactions = {
            new Transaction(201, "ACC100", 750.00, "ABC Store",
                    LocalDateTime.parse("2026-10-29T10:00:00")),
            new Transaction(202, "ACC100", 750.00, "ABC Store",
                    LocalDateTime.parse("2026-10-29T10:00:45")),
            new Transaction(203, "ACC200", 300.00, "XYZ Store",
                    LocalDateTime.parse("2026-10-29T10:05:00")),
            new Transaction(204, "ACC100", 750.00, "ABC Store",
                    LocalDateTime.parse("2026-10-29T10:02:00"))
        };

        detectDuplicates(transactions);
    }
}