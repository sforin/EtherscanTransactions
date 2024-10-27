package com.sforin.EtherscanTranscations.repository;

import com.sforin.EtherscanTranscations.dto.TransactionResponseDTO;
import com.sforin.EtherscanTranscations.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    Optional<Transaction> findByHash(String hash);
    List<Transaction> findByAddress(int address);

    @Query("SELECT new com.sforin.EtherscanTranscations.dto.TransactionResponseDTO(" +
            "a.address, t.blockNumber, t.timeStamp, t.hash, t.from, t.to, t.value, t.gasUsed) " +
            "FROM Address a JOIN Transaction t ON a.id = t.address " +
            "WHERE a.id = :address " +
            "ORDER BY t.blockNumber ASC ")
    List<TransactionResponseDTO> getTransactionResponseDTO(@Param("address")Integer address);
}
