package com.sforin.EtherscanTranscations.controller;

import com.sforin.EtherscanTranscations.model.Log;
import com.sforin.EtherscanTranscations.service.LogService;
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

    @GetMapping("/getall")
    public List<Log> getAll(){
        return logService.getAllLogs();
    }
}
