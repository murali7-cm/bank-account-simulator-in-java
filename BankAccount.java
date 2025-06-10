import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private String phoneNumber;
    private double balance;
    private String password;
    private List<Transaction> transactionHistory;
    private boolean isActive;

    public BankAccount(String accountNumber, String accountHolder, String phoneNumber, String password) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
        this.isActive = true;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
    }

    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void deposit(double amount) {
        if (amount > 0 && isActive) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
        }
    }

    public boolean transfer(BankAccount recipient, double amount) {
        if (amount > 0 && balance >= amount && isActive && recipient.isActive) {
            balance -= amount;
            recipient.balance += amount;
            transactionHistory.add(new Transaction("Transfer to " + recipient.getAccountNumber(), -amount));
            recipient.transactionHistory.add(new Transaction("Transfer from " + accountNumber, amount));
            return true;
        }
        return false;
    }

    public void deactivateAccount() {
        this.isActive = false;
        transactionHistory.add(new Transaction("Account Deactivated", 0));
    }

    public void activateAccount() {
        this.isActive = true;
        transactionHistory.add(new Transaction("Account Activated", 0));
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public boolean isActive() {
        return isActive;
    }
} 