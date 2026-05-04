package com.ebankingbackend.web;

import com.ebankingbackend.dtos.AccountHistoryDTO;
import com.ebankingbackend.dtos.AccountOperationDTO;
import com.ebankingbackend.dtos.BankAccountDTO;
import com.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.ebankingbackend.services.BankAccountService;
import com.ebankingbackend.services.BankAccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
public class BankAcountRestController {

    private final BankAccountServiceImpl bankAccountServiceImpl;
    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return  bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "5") int size ) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId, page, size);
    }




}
