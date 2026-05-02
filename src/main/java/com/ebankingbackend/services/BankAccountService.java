package com.ebankingbackend.services;

import com.ebankingbackend.entities.BankAccount;
import com.ebankingbackend.entities.CurrentAccount;
import com.ebankingbackend.entities.Customer;
import com.ebankingbackend.entities.SavingAccount;
import com.ebankingbackend.exceptions.BalanceNotSufficentException;
import com.ebankingbackend.exceptions.BankAccountNotFoundException;

import java.util.List;

public interface BankAccountService {

    Customer saveCustomer(Customer customer);

    CurrentAccount saveCurrentBankAccount(double initialBalance, Long customerId, double overDraft );

    SavingAccount saveSavingBankAccount(double initialBalance, Long customerId, double interestRate);

    List<Customer> listCustomers();

    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;

    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException;

    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;

    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException;

}
