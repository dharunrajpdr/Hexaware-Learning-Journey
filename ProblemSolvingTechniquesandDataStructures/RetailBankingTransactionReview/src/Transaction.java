import java.time.LocalDateTime;

public class Transaction {
    private final int transactionId;
    private final String accountNumber;
    private final double amount;
    private final String merchant;
    private final LocalDateTime postingDate;

    public Transaction(int transactionId, String accountNumber,
                       double amount, String merchant,
                       LocalDateTime postingDate) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.merchant = merchant;
        this.postingDate = postingDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getMerchant() {
        return merchant;
    }

    public LocalDateTime getPostingDate() {
        return postingDate;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionId
                + ", Account: " + accountNumber
                + ", Amount: " + amount
                + ", Merchant: " + merchant
                + ", Date: " + postingDate;
    }
}