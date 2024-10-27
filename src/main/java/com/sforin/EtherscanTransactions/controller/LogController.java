package com.sforin.EtherscanTransactions.controller;

import com.sforin.EtherscanTransactions.model.Log;
import com.sforin.EtherscanTransactions.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/log")
public class LogController {
    @Autowired
    private LogService logService;

    /**
     * Retrieves a list of all log entries.
     * This method fetches all log records stored in the system and returns them as a list.
     * Useful for auditing and tracking all recorded operations and activities.
     *
     * @return a list of Log objects representing all log entries in the database
     */

    @GetMapping("/getall")
    public List<Log> getAll(){
        return logService.getAllLogs();
    }
}
