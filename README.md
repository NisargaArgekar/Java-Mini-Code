# Banking Program – Java Console Application

A simple Banking System Console Application built in Java.

This mini-project is perfect for learning input handling, loops, switch cases, validation, and method-based programming.

It is commonly asked in Java interviews and assignments.

🚀 Features
1. Show account balance
2. Deposit amount (with validation)
3. Withdraw amount (with insufficient balance check)
4. Exit the program
5. User-friendly menu-driven interface
6. Input validation for secure transactions

🧠 Concepts Used
- Scanner for input
- While loops
- Switch expressions
- Functions (methods)
- Conditional checks
- Formatted output using printf

📂 Project Structure

BankingProgram.java

│

├── showBalance()   → Displays current balance

├── deposit()       → Handles user deposit with validation

└── withdraw()      → Handles withdrawal with validation


📸 Sample Output

**-----> BANKING PROGRAM <-----**

1. Show Balance
2. Deposit
3. Withdraw
4. Exit


Enter your choice (1-4): 2
Enter an amount to be deposited: 500


🛠️ How to Run

-> Clone the repository:

    git clone https://github.com/your-username/banking-program.git
    
-> Navigate into the project folder:

    cd banking-program
    
-> Compile the Java file:

    javac BankingProgram.java
    
-> Run the program:

    java BankingProgram

🧩 Code Snippet (Main Logic)

switch(choice)
{

    case 1 -> showBalance(balance);
    case 2 -> balance += deposit();
    case 3 -> balance -= withdraw(balance);
    case 4 -> isRunning = false;
    default -> System.out.println("INVALID CHOICE");
}

🚀 Future Enhancements:

🔐 Add PIN-based login.

📄 Add transaction history.

🏦 Create BankAccount class (OOP version).

💾 Store data using file handling (txt/json).

🖥️ Add UI using Java Swing or JavaFX

🤝 Contributing

Pull requests are welcome.
For major changes, please open an issue first to discuss what you would like to improve.

📜 License

This project is open-source and free to use.
This project is open-source and free to use.
