package com.sforin.EtherscanTranscations.repository;

import com.sforin.EtherscanTranscations.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {
}
