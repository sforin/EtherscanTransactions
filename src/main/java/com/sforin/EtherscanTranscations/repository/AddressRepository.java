package com.sforin.EtherscanTranscations.repository;

import com.sforin.EtherscanTranscations.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<Address> findByAddress(String address);
}
