package net.saifi.ebankingbackend.web;

import net.saifi.ebankingbackend.dtos.AccountHistoryDTO;
import net.saifi.ebankingbackend.dtos.AccountOperationDTO;
import net.saifi.ebankingbackend.dtos.BankAccountDTO;
import net.saifi.ebankingbackend.exceptions.BankAccountNotFoundException;
import net.saifi.ebankingbackend.services.BankAccountService;
import net.saifi.ebankingbackend.services.BankAccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
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
