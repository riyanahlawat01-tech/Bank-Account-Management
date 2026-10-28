package com.bankapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank account with basic operations.
 * @author Riyan Ahlawat
 */
public class BankAccount {

    private String accountNumber;
    private String holderName;
    private double balance;
    private List<String> transactions;

    /**
     * Constructor to create a new BankAccount.
     * @param accountNumber unique account number
     * @param holderName name of account holder
     * @param initialDeposit opening balance
     */
    public BankAccount(String accountNumber, String holderName, double initialDeposit) {
        if (accountNumber == null || accountNumber.trim().isEmpty())
            throw new IllegalArgumentException("Account number cannot be empty.");
        if (holderName == null || holderName.trim().isEmpty())
            throw new IllegalArgumentException("Holder name cannot be empty.");
        if (initialDeposit < 0)
            throw new IllegalArgumentException("Initial deposit cannot be negative.");

        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialDeposit;
        this.transactions = new ArrayList<>();
        transactions.add("Account opened with Rs. " + initialDeposit);
    }

    /** @return account number */
    public String getAccountNumber() { return accountNumber; }

    /** @return holder name */
    public String getHolderName() { return holderName; }

    /** @return current balance */
    public double getBalance() { return balance; }

    /** @return transaction list */
    public List<String> getTransactions() { return transactions; }

    /**
     * Deposits money into the account.
     * @param amount amount to deposit
     */
    public void deposit(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Deposit amount must be positive.");
        balance += amount;
        transactions.add("Deposited: Rs. " + amount + " | Balance: Rs. " + balance);
    }

    /**
     * Withdraws money from the account.
     * @param amount amount to withdraw
     */
    public void withdraw(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        if (amount > balance)
            throw new IllegalArgumentException("Insufficient balance.");
        balance -= amount;
        transactions.add("Withdrawn: Rs. " + amount + " | Balance: Rs. " + balance);
    }

    /**
     * Transfers money to another account.
     * @param target target BankAccount
     * @param amount amount to transfer
     */
    public void transfer(BankAccount target, double amount) {
        if (target == null)
            throw new IllegalArgumentException("Target account cannot be null.");
        if (amount <= 0)
            throw new IllegalArgumentException("Transfer amount must be positive.");
        if (amount > balance)
            throw new IllegalArgumentException("Insufficient balance for transfer.");
        balance -= amount;
        target.balance += amount;
        transactions.add("Transferred: Rs. " + amount + " to " + target.getAccountNumber() + " | Balance: Rs. " + balance);
        target.transactions.add("Received: Rs. " + amount + " from " + this.accountNumber + " | Balance: Rs. " + target.balance);
    }

    /**
     * Returns formatted account statement.
     * @return statement string
     */
    public String getStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Statement for ").append(holderName).append(" [").append(accountNumber).append("] ===\n");
        for (String t : transactions) {
            sb.append("  ").append(t).append("\n");
        }
        sb.append("Current Balance: Rs. ").append(balance).append("\n");
        return sb.toString();
    }
}
