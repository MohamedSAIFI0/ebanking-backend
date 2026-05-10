package net.saifi.ebankingbackend.services;

import com.ebankingbackend.dtos.*;
import com.ebankingbackend.entities.*;
import net.saifi.ebankingbackend.dtos.*;
import net.saifi.ebankingbackend.entities.*;
import net.saifi.ebankingbackend.enums.OperationType;
import net.saifi.ebankingbackend.exceptions.BalanceNotSufficentException;
import net.saifi.ebankingbackend.exceptions.BankAccountNotFoundException;
import net.saifi.ebankingbackend.exceptions.CustomerNotFoundException;
import net.saifi.ebankingbackend.mappers.BankAccountMapperImpl;
import net.saifi.ebankingbackend.repositories.AccountOperationRepository;
import net.saifi.ebankingbackend.repositories.BankAccountRepository;
import net.saifi.ebankingbackend.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {


    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, Long customerId, double overDraft) {
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

        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, Long customerId, double interestRate) {
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

        return dtoMapper.fromSavingBankAccount(savingAccount);
    }


    @Override
    public List<CustomerDTO> listCustomers() {
         List<Customer> customers = customerRepository.findAll();
         List<CustomerDTO> customerDTOS =  customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        /* List<CustomerDTO> customerDTOS = new ArrayList<>();
         for(Customer customer:customers){
             CustomerDTO customerDTO = dtoMapper.fromCustomer(customer);
             customerDTOS.add(customerDTO);
         } */
         return customerDTOS;

    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("BankAccount Not Found"));
        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount= (SavingAccount) bankAccount;
            return dtoMapper.fromSavingBankAccount(savingAccount);

        }else{
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentBankAccount(currentAccount);
        }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("BankAccount Not Found"));
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
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("BankAccount Not Found"));
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

    @Override
    public List<BankAccountDTO> bankAccountList(){

        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS =  bankAccounts.stream().map(bankAccount -> {
            if(bankAccount instanceof SavingAccount){
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return dtoMapper.fromSavingBankAccount(savingAccount);
            }else{
                CurrentAccount currentAccount = (CurrentAccount)  bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }

    @Override
    public CustomerDTO getCustomer(Long customerId){
        Customer customer =  customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer Not Found"));
        return dtoMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO){
        log.info("Update Customer");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));

        // Get all bank accounts for this customer
        List<BankAccount> bankAccounts = bankAccountRepository.findByCustomer_Id(customerId);

        for (BankAccount bankAccount : bankAccounts) {
            // Delete all operations for each account first
            accountOperationRepository.deleteByBankAccount_Id(bankAccount.getId());
        }

        // Now safe to delete the customer (and their accounts)
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccount_Id(accountId);
        return accountOperations.stream().map(op-> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount == null) throw new BankAccountNotFoundException("Account Not Found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccount_Id(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword){
        List<Customer> customers = customerRepository.searchCustomer(keyword);

        List<CustomerDTO> customersDTO = customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customersDTO;
    }
}
