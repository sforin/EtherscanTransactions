package com.sforin.EtherscanTranscations.repository;

import com.sforin.EtherscanTranscations.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    Optional<Transaction> findByHash(String hash);
    List<Transaction> findByAddress(int address);
}
