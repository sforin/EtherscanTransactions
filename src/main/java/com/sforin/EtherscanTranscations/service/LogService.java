package com.sforin.EtherscanTranscations.service;

import com.sforin.EtherscanTranscations.model.Log;
import com.sforin.EtherscanTranscations.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    public void saveLog(Timestamp timestamp, String operation, String additional_info) {
        Log log = new Log();
        log.setTimestamp(timestamp);
        log.setOperation(operation);
        log.setAdditionalInfo(additional_info);

        logRepository.save(log);
    }

    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }
}
