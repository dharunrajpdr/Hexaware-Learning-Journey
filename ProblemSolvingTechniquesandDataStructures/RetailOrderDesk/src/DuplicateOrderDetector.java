import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class DuplicateOrderDetector {

    public static void main(String[] args) {
        Map<String, Integer> seenOrders = new HashMap<>();

        Order[] orders = {
            new Order(101, 10, 500,
                    LocalDateTime.parse("2026-10-29T10:35:12")),
            new Order(102, 10, 500,
                    LocalDateTime.parse("2026-10-29T10:35:48")),
            new Order(103, 11, 700,
                    LocalDateTime.parse("2026-10-29T10:40:10"))
        };

        for (Order order : orders) {
            LocalDateTime minute = order.getCreatedAt()
                    .truncatedTo(ChronoUnit.MINUTES);

            String key = order.getCustomerId() + "|"
                    + order.getAmount() + "|" + minute;

            if (seenOrders.containsKey(key)) {
                System.out.println("Possible duplicate found:");
                System.out.println("Original Order ID: "
                        + seenOrders.get(key));
                System.out.println("Duplicate Order ID: "
                        + order.getOrderId());
            } else {
                seenOrders.put(key, order.getOrderId());
            }
        }
    }
}