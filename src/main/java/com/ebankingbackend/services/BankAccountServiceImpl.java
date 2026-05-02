package com.ebankingbackend.services;

import com.ebankingbackend.entities.*;
import com.ebankingbackend.enums.OperationType;
import com.ebankingbackend.exceptions.BalanceNotSufficentException;
import com.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.ebankingbackend.exceptions.CustomerNotFoundException;
import com.ebankingbackend.repositories.AccountOperationRepository;
import com.ebankingbackend.repositories.BankAccountRepository;
import com.ebankingbackend.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;


    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("Saving new customer");
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, Long customerId, double overDraft) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);

        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);

        return savedBankAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, Long customerId, double interestRate) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);

        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);

        return savingAccount;
    }


    @Override
    public List<Customer> listCustomers() {
        return List.of();
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("BankAccount Not Found"));
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException {
        BankAccount bankAccount = getBankAccount(accountId);

        if(bankAccount.getBalance() < amount){
            throw new
                    BalanceNotSufficentException("Balance not sufficient");
        }

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = getBankAccount(accountId);

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException {
        debit(accountIdSource, amount, "Transfer to "+accountIdDestination );
        credit(accountIdDestination, amount, "Tranfer from"+accountIdSource);
    }
}
