package com.sforin.EtherscanTransactions.repository;

import com.sforin.EtherscanTransactions.dto.TransactionResponseDTO;
import com.sforin.EtherscanTransactions.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    /**
     * Retrieves an optional transaction based on its unique hash.
     *
     * @param hash the unique hash identifier of the transaction
     * @return an Optional containing the transaction if found, or an empty Optional if not found
     */
    Optional<Transaction> findByHash(String hash);

    /**
     * Retrieves a list of transactions associated with the specified address ID.
     *
     * @param address the ID of the address for which to find transactions
     * @return a list of transactions associated with the given address
     */
    List<Transaction> findByAddress(int address);

    /**
     * Retrieves a list of transaction response DTOs for a given address ID.
     * The result contains specific fields from the transaction and address entities,
     * ordered by block number in ascending order.
     *
     * @param address the ID of the address for which to retrieve transaction details
     * @return a list of TransactionResponseDTOs containing the transaction details for the specified address
     */
    @Query("SELECT new com.sforin.EtherscanTransactions.dto.TransactionResponseDTO(" +
            "a.address, t.blockNumber, t.timeStamp, t.hash, t.from, t.to, t.value, t.gasUsed, a.balance) " +
            "FROM Address a JOIN Transaction t ON a.id = t.address " +
            "WHERE a.id = :address " +
            "ORDER BY t.blockNumber ASC ")
    List<TransactionResponseDTO> getTransactionResponseDTO(@Param("address")Integer address);
}
