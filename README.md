# 🏦 Java Mini Banking System

A clean, console-based Java application that simulates real banking operations using Object-Oriented Programming and persistent data storage. Designed as a learning project to demonstrate Java fundamentals, modular structure, and basic backend logic.


## 🚀 Features

- Account Creation – Register a new bank account with a secure 4-digit PIN.
- **PIN-Based Login** – Only authenticated users can access their accounts.
- **Deposit & Withdrawal** – Perform transactions with proper validation.
- **Balance Inquiry** – View the current account balance anytime.
- **Transaction History** – Every transaction is logged with a timestamp and saved.
- **Persistent Storage** – All accounts and transactions are stored using file serialization (`bank_accounts.dat`).
- **Multiple Account Support** – System can store and manage any number of users.


## 📂 Project Structure

BankingSystem/
│── Main.java # Entry point + User Interface (Console)
│── BankAccount.java # Account model + balance operations
│── Transaction.java # Transaction model (type, amount, time)
│── BankService.java # Handles account management, login, validation
└── FileStorage.java # Saves/loads data using Java Serialization


## 💻 How to Run the Project

1. Place all `.java` files inside a folder named **practice**.
2. Open a terminal in the folder that contains the `practice` directory.
3. Compile all Java files:
   ```
   javac BankingSystem/*.java

4. Run the program:
   ```
   java practice.Main

5. Follow the on-screen instructions to create an account, log in, and manage your banking operations.

🎯 Purpose of This Project

This project demonstrates:

-> Clean Object-Oriented Programming (OOP)

-> Java file handling & serialization

-> Input validation & exception handling

-> Modular project structure

-> Real-world banking logic at a beginner-friendly level

📜 License

This project is open-source. You may modify or extend it for learning or practice.



