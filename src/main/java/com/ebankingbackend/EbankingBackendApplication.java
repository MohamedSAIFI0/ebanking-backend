package com.ebankingbackend;

import com.ebankingbackend.entities.*;
import com.ebankingbackend.enums.AccountStatus;
import com.ebankingbackend.repositories.AccountOperationRepository;
import com.ebankingbackend.repositories.BankAccountRepository;
import com.ebankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("hassan", "yassine", "mohamed").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(customer ->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setCustomer(customer);
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);


                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setCustomer(customer);
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setInterestRate(900);
                bankAccountRepository.save(savingAccount);
            });


        };
    }


}
