package com.bankapp;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages all bank accounts. Provides create and lookup operations.
 * @author Riyan Ahlawat
 */
public class Bank {

    private String bankName;
    private Map<String, BankAccount> accounts;

    /**
     * Constructor for Bank.
     * @param bankName name of the bank
     */
    public Bank(String bankName) {
        this.bankName = bankName;
        this.accounts = new HashMap<>();
    }

    /** @return bank name */
    public String getBankName() { return bankName; }

    /**
     * Creates and stores a new account.
     * @param accountNumber unique account number
     * @param holderName holder name
     * @param initialDeposit opening balance
     * @return created BankAccount
     */
    public BankAccount createAccount(String accountNumber, String holderName, double initialDeposit) {
        if (accounts.containsKey(accountNumber))
            throw new IllegalArgumentException("Account number already exists: " + accountNumber);
        BankAccount account = new BankAccount(accountNumber, holderName, initialDeposit);
        accounts.put(accountNumber, account);
        return account;
    }

    /**
     * Finds an account by account number.
     * @param accountNumber the account number to search
     * @return BankAccount or null if not found
     */
    public BankAccount findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    /** @return number of accounts in the bank */
    public int getTotalAccounts() { return accounts.size(); }
}
