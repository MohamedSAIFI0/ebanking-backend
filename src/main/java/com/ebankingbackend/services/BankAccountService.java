package com.ebankingbackend.services;

import com.ebankingbackend.dtos.BankAccountDTO;
import com.ebankingbackend.dtos.CurrentBankAccountDTO;
import com.ebankingbackend.dtos.CustomerDTO;
import com.ebankingbackend.dtos.SavingBankAccountDTO;
import com.ebankingbackend.entities.BankAccount;
import com.ebankingbackend.exceptions.BalanceNotSufficentException;
import com.ebankingbackend.exceptions.BankAccountNotFoundException;

import java.util.List;

public interface BankAccountService {

    CustomerDTO saveCustomer(CustomerDTO customer);

    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, Long customerId, double overDraft );

    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, Long customerId, double interestRate);

    List<CustomerDTO> listCustomers();

    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;

    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException;

    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;

    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);
}
