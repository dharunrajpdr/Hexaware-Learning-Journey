import java.time.LocalDateTime;

public class Order {
    private int orderId;
    private int customerId;
    private double amount;
    private LocalDateTime createdAt;

    public Order(int orderId, int customerId, double amount,
                 LocalDateTime createdAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId
                + ", Customer ID: " + customerId
                + ", Amount: " + amount
                + ", Created At: " + createdAt;
    }
}