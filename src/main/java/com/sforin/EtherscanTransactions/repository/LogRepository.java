package com.sforin.EtherscanTransactions.repository;

import com.sforin.EtherscanTransactions.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {
}
