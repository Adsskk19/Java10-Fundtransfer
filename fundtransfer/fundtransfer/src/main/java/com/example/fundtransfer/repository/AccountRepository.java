package com.example.fundtransfer.repository;

import com.example.fundtransfer.entity.Account;
import jakarta.persistence.LockModeType;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByCustomerId(Long customerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Account findByAccountNumber(String accountNumber);
}
