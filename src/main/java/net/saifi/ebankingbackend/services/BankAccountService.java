package net.saifi.ebankingbackend.services;

import com.ebankingbackend.dtos.*;
import net.saifi.ebankingbackend.dtos.*;
import net.saifi.ebankingbackend.exceptions.BalanceNotSufficentException;
import net.saifi.ebankingbackend.exceptions.BankAccountNotFoundException;

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

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;


    List<CustomerDTO> searchCustomers(String keyword);
}
