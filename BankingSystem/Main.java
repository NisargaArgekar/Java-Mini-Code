package BankingSystem;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String STORAGE_FILE = "bank_accounts.dat";
    private static final int MAX_PIN_ATTEMPTS = 3;

    public static void main(String[] args) {
        FileStorage storage = new FileStorage(STORAGE_FILE);
        BankService service = new BankService(storage);
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Welcome to MiniBank (Console) ===");

        outerLoop:
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1) Create new account");
            System.out.println("2) Login to existing account");
            System.out.println("3) List accounts (admin view)");
            System.out.println("4) Exit");
            System.out.print("Choose (1-4): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    createAccountFlow(scanner, service);
                    break;
                case "2":
                    BankAccount loggedIn = loginFlow(scanner, service);
                    if (loggedIn != null) accountMenu(scanner, service, loggedIn);
                    break;
                case "3":
                    adminList(service);
                    break;
                case "4":
                    System.out.println("Saving data and exiting...");
                    service.saveAll();
                    break outerLoop;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    private static void createAccountFlow(Scanner scanner, BankService service) {
        System.out.println("\n== Create Account ==");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        String pin = askForPin(scanner);
        if (pin == null) {
            System.out.println("PIN setup cancelled.");
            return;
        }

        BankAccount acc = service.createAccount(name, pin);
        System.out.println("Account created successfully!");
        System.out.println("Account Number: " + acc.getAccountNumber());
        System.out.printf("Owner: %s | Balance: %.2f%n", acc.getOwnerName(), acc.getBalance());
    }

    private static String askForPin(Scanner scanner) {
        System.out.print("Set a 4-digit PIN: ");
        String pin1 = scanner.nextLine().trim();
        if (!pin1.matches("\\d{4}")) {
            System.out.println("PIN must be exactly 4 digits.");
            return null;
        }
        System.out.print("Confirm PIN: ");
        String pin2 = scanner.nextLine().trim();
        if (!pin1.equals(pin2)) {
            System.out.println("PINs do not match.");
            return null;
        }
        return pin1;
    }

    private static BankAccount loginFlow(Scanner scanner, BankService service) {
        System.out.println("\n== Login ==");
        System.out.print("Enter Account Number: ");
        String accNo = scanner.nextLine().trim();
        BankAccount acc = service.getAccount(accNo);
        if (acc == null) {
            System.out.println("Account not found.");
            return null;
        }

        int attempts = 0;
        while (attempts < MAX_PIN_ATTEMPTS) {
            System.out.print("Enter 4-digit PIN: ");
            String pin = scanner.nextLine().trim();
            if (acc.checkPin(pin)) {
                System.out.println("Login successful. Welcome, " + acc.getOwnerName() + "!");
                return acc;
            } else {
                attempts++;
                System.out.println("Incorrect PIN. Attempts left: " + (MAX_PIN_ATTEMPTS - attempts));
            }
        }
        System.out.println("Too many failed attempts. Returning to main menu.");
        return null;
    }

    private static void accountMenu(Scanner scanner, BankService service, BankAccount acc) {
        while (true) {
            System.out.println("\nAccount Menu (" + acc.getAccountNumber() + "):");
            System.out.println("1) Show balance");
            System.out.println("2) Deposit");
            System.out.println("3) Withdraw");
            System.out.println("4) Transaction history");
            System.out.println("5) Change PIN");
            System.out.println("6) Logout");
            System.out.print("Choose (1-6): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.printf("Current Balance: %.2f%n", acc.getBalance());
                    break;
                case "2":
                    doDeposit(scanner, service, acc);
                    break;
                case "3":
                    doWithdraw(scanner, service, acc);
                    break;
                case "4":
                    showHistory(acc);
                    break;
                case "5":
                    changePin(scanner, service, acc);
                    break;
                case "6":
                    service.saveAll();
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void doDeposit(Scanner scanner, BankService service, BankAccount acc) {
        System.out.print("Enter amount to deposit: ");
        String s = scanner.nextLine().trim();
        try {
            double amt = Double.parseDouble(s);
            if (amt <= 0) {
                System.out.println("Enter a positive amount.");
                return;
            }
            acc.deposit(amt);
            service.saveAll();
            System.out.printf("Deposit successful. New balance: %.2f%n", acc.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void doWithdraw(Scanner scanner, BankService service, BankAccount acc) {
        System.out.print("Enter amount to withdraw: ");
        String s = scanner.nextLine().trim();
        try {
            double amt = Double.parseDouble(s);
            if (amt <= 0) {
                System.out.println("Enter a positive amount.");
                return;
            }
            boolean ok = acc.withdraw(amt);
            if (ok) {
                service.saveAll();
                System.out.printf("Withdrawal successful. New balance: %.2f%n", acc.getBalance());
            } else {
                System.out.println("Insufficient balance.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void showHistory(BankAccount acc) {
        List<Transaction> hist = acc.getHistory();
        if (hist.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.println("\n=== Transaction History ===");
        for (Transaction t : hist) {
            System.out.println(t);
        }
    }

    private static void changePin(Scanner scanner, BankService service, BankAccount acc) {
        System.out.print("Enter current PIN: ");
        String curr = scanner.nextLine().trim();
        if (!acc.checkPin(curr)) {
            System.out.println("Incorrect current PIN.");
            return;
        }
        System.out.print("Enter new 4-digit PIN: ");
        String newPin = scanner.nextLine().trim();
        if (!newPin.matches("\\d{4}")) {
            System.out.println("PIN must be exactly 4 digits.");
            return;
        }
        System.out.print("Confirm new PIN: ");
        String confirm = scanner.nextLine().trim();
        if (!newPin.equals(confirm)) {
            System.out.println("PINs do not match.");
            return;
        }
        acc.setPin(newPin);
        service.saveAll();
        System.out.println("PIN changed successfully.");
    }

    private static void adminList(BankService service) {
        System.out.println("\n== All Accounts ==");
        for (BankAccount a : service.listAllAccounts()) {
            System.out.println(a);
        }
    }
}

