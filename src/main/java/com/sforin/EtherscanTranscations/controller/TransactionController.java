package com.sforin.EtherscanTranscations.controller;

import com.sforin.EtherscanTranscations.model.Transaction;
import com.sforin.EtherscanTranscations.service.EtherscanService;
import com.sforin.EtherscanTranscations.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    @Autowired
    private  EtherscanService etherscanService;
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/update")
    public ResponseEntity<String> updateTransactions(@RequestParam String address) {
        transactionService.updateTransactions(address);
        return ResponseEntity.ok("Transaction updated successfully");
    }

    @GetMapping("/list")
    public ResponseEntity<List<Transaction>> getTransactions(@RequestParam String address) {
        List<Transaction> transactions = transactionService.getTransactionsByAddress(address);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{address}")
    public List<Transaction> getTransactions2(@PathVariable String address) {
        transactionService.updateTransactions(address);
        return transactionService.getTransactionsByAddress(address);
    }
}
