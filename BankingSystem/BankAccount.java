package BankingSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BankAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String accountNumber;
    private final String ownerName;
    private String pin; // kept as string for simplicity (could store hashed)
    private double balance;
    private final List<Transaction> history;

    public BankAccount(String accountNumber, String ownerName, String pin) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.pin = pin;
        this.balance = 0.0;
        this.history = new ArrayList<>();
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public double getBalance() { return balance; }

    public boolean checkPin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void setPin(String newPin) {
        this.pin = newPin;
    }

    public synchronized void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be positive.");
        balance += amount;
        history.add(new Transaction(Transaction.Type.DEPOSIT, amount, balance));
    }

    /**
     * Attempts to withdraw. Returns true if successful, false if insufficient funds.
     */
    public synchronized boolean withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal must be positive.");
        if (amount > balance) return false;
        balance -= amount;
        history.add(new Transaction(Transaction.Type.WITHDRAWAL, amount, balance));
        return true;
    }

    public List<Transaction> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public String toString() {
        return String.format("Account[%s] Owner: %s Balance: %.2f", accountNumber, ownerName, balance);
    }
}

