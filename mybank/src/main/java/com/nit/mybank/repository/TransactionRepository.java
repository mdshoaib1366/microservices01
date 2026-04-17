package com.nit.mybank.repository;

import com.nit.mybank.entity.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Optional<Transaction> findByReferenceId(String referenceId);

    @Modifying
    @Transactional
    @Query("""
    DELETE FROM Transaction t 
    WHERE t.fromAccount.id = :accountId 
       OR t.toAccount.id = :accountId
    """)
    void deleteByAccountId(UUID accountId);
}