package net.saifi.ebankingbackend.repositories;

import net.saifi.ebankingbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    List<BankAccount> findByCustomer_Id(Long customerId);
}
