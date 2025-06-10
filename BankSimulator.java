import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankSimulator {
    private Map<String, BankAccount> accounts;
    private Scanner scanner;
    private static final String ADMIN_PASSWORD = "admin123"; // In real application, this should be securely stored

    public BankSimulator() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Bank Account Management System ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    System.out.println("Thank you for using the Bank Account Management System!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void adminLogin() {
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        if (password.equals(ADMIN_PASSWORD)) {
            adminMenu();
        } else {
            System.out.println("Invalid admin password!");
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Create New Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Transfer Money");
            System.out.println("4. View Account Details");
            System.out.println("5. Deactivate Account");
            System.out.println("6. Activate Account");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    transferMoney();
                    break;
                case 4:
                    viewAccountDetails();
                    break;
                case 5:
                    deactivateAccount();
                    break;
                case 6:
                    activateAccount();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account number already exists!");
            return;
        }

        System.out.print("Enter account holder name: ");
        String accountHolder = scanner.nextLine();

        String phoneNumber;
        do {
            System.out.print("Enter phone number (10 digits): ");
            phoneNumber = scanner.nextLine();
            if (!BankAccount.isValidPhoneNumber(phoneNumber)) {
                System.out.println("Invalid phone number! Please enter exactly 10 digits.");
            }
        } while (!BankAccount.isValidPhoneNumber(phoneNumber));

        System.out.print("Enter account password: ");
        String password = scanner.nextLine();

        accounts.put(accountNumber, new BankAccount(accountNumber, accountHolder, phoneNumber, password));
        System.out.println("Account created successfully!");
    }

    private void depositMoney() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        BankAccount account = accounts.get(accountNumber);

        if (account != null) {
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            account.deposit(amount);
            System.out.println("Deposit successful!");
        } else {
            System.out.println("Account not found!");
        }
    }

    private void transferMoney() {
        System.out.print("Enter source account number: ");
        String sourceAccount = scanner.nextLine();
        System.out.print("Enter destination account number: ");
        String destAccount = scanner.nextLine();

        BankAccount source = accounts.get(sourceAccount);
        BankAccount destination = accounts.get(destAccount);

        if (source != null && destination != null) {
            System.out.print("Enter amount to transfer: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            if (source.transfer(destination, amount)) {
                System.out.println("Transfer successful!");
            } else {
                System.out.println("Transfer failed! Check account status and balance.");
            }
        } else {
            System.out.println("One or both accounts not found!");
        }
    }

    private void viewAccountDetails() {
        System.out.println("\nView Account Details");
        System.out.println("1. By Account Number");
        System.out.println("2. By Phone Number");
        System.out.print("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        BankAccount account = null;
        
        switch (choice) {
            case 1:
                System.out.print("Enter account number: ");
                String accountNumber = scanner.nextLine();
                account = accounts.get(accountNumber);
                break;
                
            case 2:
                String phoneNumber;
                do {
                    System.out.print("Enter phone number (10 digits): ");
                    phoneNumber = scanner.nextLine();
                    if (!BankAccount.isValidPhoneNumber(phoneNumber)) {
                        System.out.println("Invalid phone number! Please enter exactly 10 digits.");
                        continue;
                    }
                    
                    // Search for account with matching phone number
                    for (BankAccount acc : accounts.values()) {
                        if (acc.getPhoneNumber().equals(phoneNumber)) {
                            account = acc;
                            break;
                        }
                    }
                    
                    if (account == null) {
                        System.out.println("No account found with this phone number!");
                    }
                } while (!BankAccount.isValidPhoneNumber(phoneNumber) || account == null);
                break;
                
            default:
                System.out.println("Invalid option!");
                return;
        }

        if (account != null) {
            System.out.println("\nAccount Details:");
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Holder: " + account.getAccountHolder());
            System.out.println("Phone Number: " + account.getPhoneNumber());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("Status: " + (account.isActive() ? "Active" : "Inactive"));
            System.out.println("\nTransaction History:");
            for (Transaction t : account.getTransactionHistory()) {
                System.out.println(t);
            }
        }
    }

    private void deactivateAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        BankAccount account = accounts.get(accountNumber);

        if (account != null) {
            account.deactivateAccount();
            System.out.println("Account deactivated successfully!");
        } else {
            System.out.println("Account not found!");
        }
    }

    private void activateAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        BankAccount account = accounts.get(accountNumber);

        if (account != null) {
            account.activateAccount();
            System.out.println("Account activated successfully!");
        } else {
            System.out.println("Account not found!");
        }
    }

    public static void main(String[] args) {
        BankSimulator simulator = new BankSimulator();
        simulator.start();
    }
}