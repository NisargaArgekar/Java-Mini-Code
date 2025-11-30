package BankingSystem;

import java.util.*;

/**
 * Manages accounts in memory; delegates persistence to FileStorage.
 */
public class BankService {
    private final Map<String, BankAccount> accounts; // key = accountNumber
    private final FileStorage storage;
    private final Random random = new Random();

    public BankService(FileStorage storage) {
        this.storage = storage;
        this.accounts = storage.load();
    }

    public synchronized void saveAll() {
        storage.save(accounts);
    }

    /**
     * Creates a new account with a unique numeric 8-digit account number.
     * Returns the created BankAccount.
     */
    public synchronized BankAccount createAccount(String ownerName, String pin) {
        String accNo = generateUniqueAccountNumber();
        BankAccount acc = new BankAccount(accNo, ownerName, pin);
        accounts.put(accNo, acc);
        saveAll();
        return acc;
    }

    /**
     * Login attempt: return BankAccount if account exists and pin matches, otherwise null.
     */
    public BankAccount login(String accountNumber, String pin) {
        BankAccount acc = accounts.get(accountNumber);
        if (acc == null) return null;
        return acc.checkPin(pin) ? acc : null;
    }

    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public Collection<BankAccount> listAllAccounts() {
        return Collections.unmodifiableCollection(accounts.values());
    }

    private String generateUniqueAccountNumber() {
        // 8-digit numeric string, ensure unique
        String acc;
        do {
            int n = 10000000 + random.nextInt(90000000); // 8-digit
            acc = Integer.toString(n);
        } while (accounts.containsKey(acc));
        return acc;
    }
}

