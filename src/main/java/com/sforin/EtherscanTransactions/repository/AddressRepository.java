package com.sforin.EtherscanTransactions.repository;

import com.sforin.EtherscanTransactions.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    /**
     * Retrieves an optional Address entity based on the provided Ethereum address.
     *
     * @param address the Ethereum address to search for
     * @return an Optional containing the Address if found, or an empty Optional if not found
     */
    Optional<Address> findByAddress(String address);
}
