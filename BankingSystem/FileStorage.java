package BankingSystem;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple object-serialization based storage.
 * Saves/loads a Map<String, BankAccount> from disk.
 */
public class FileStorage {
    private final String filename;

    public FileStorage(String filename) {
        this.filename = filename;
    }

    /**
     * Save all accounts to file (overwrites).
     */
    public void save(Map<String, BankAccount> accounts) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(accounts);
        } catch (IOException e) {
            System.err.println("Failed to save accounts: " + e.getMessage());
        }
    }

    /**
     * Load accounts file. If not present or error, returns empty map.
     */
    @SuppressWarnings("unchecked")
    public Map<String, BankAccount> load() {
        File f = new File(filename);
        if (!f.exists()) return new HashMap<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = in.readObject();
            if (obj instanceof Map) {
                return (Map<String, BankAccount>) obj;
            } else {
                System.err.println("Storage file corrupted, starting fresh.");
                return new HashMap<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load accounts: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
