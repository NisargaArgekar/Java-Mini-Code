package BankingSystem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Type { DEPOSIT, WITHDRAWAL }

    private final Type type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final double postBalance; // balance after this transaction

    public Transaction(Type type, double amount, double postBalance) {
        this.type = type;
        this.amount = amount;
        this.postBalance = postBalance;
        this.timestamp = LocalDateTime.now();
    }

    public Type getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public double getPostBalance() { return postBalance; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s | %s | Amount: %.2f | Balance after: %.2f",
                fmt.format(timestamp),
                type == Type.DEPOSIT ? "Deposit   " : "Withdraw  ",
                amount,
                postBalance);
    }
}
