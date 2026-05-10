package net.saifi.ebankingbackend.repositories;

import net.saifi.ebankingbackend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    public List<AccountOperation> findByBankAccount_Id(String accountId);

    Page<AccountOperation> findByBankAccount_Id(String accountId, Pageable pageable);
    void deleteByBankAccount_Id(String accountId);

}
