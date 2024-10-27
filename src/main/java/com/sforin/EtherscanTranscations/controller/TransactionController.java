package com.sforin.EtherscanTranscations.controller;

import com.sforin.EtherscanTranscations.dto.APIResponseDTO;
import com.sforin.EtherscanTranscations.dto.TransactionResponseDTO;
import com.sforin.EtherscanTranscations.enums.ResponseDTOStatus;
import com.sforin.EtherscanTranscations.service.EtherscanService;
import com.sforin.EtherscanTranscations.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    @Autowired
    private  EtherscanService etherscanService;
    @Autowired
    private TransactionService transactionService;

    /**
     *
     * @param address
     * @return
     */
    @GetMapping("/update")
    public ResponseEntity<String> updateTransactions(@RequestParam String address) {
        transactionService.updateTransactions(address);
        return ResponseEntity.ok("Transaction updated successfully");
    }

    /**
     *
     * @param address
     * @return
     */
    @GetMapping("/{address}")
    public ResponseEntity<APIResponseDTO> getTransactions(@PathVariable String address) {
        ResponseEntity<APIResponseDTO> updateResponse = transactionService.updateTransactions(address);
        //if the update goes well
        if (updateResponse.getStatusCode().is2xxSuccessful() && Objects.requireNonNull(updateResponse.getBody()).getStatus().equals(ResponseDTOStatus.OK)) {
            List<TransactionResponseDTO> transactions = transactionService.getTransactionsByAddress(address);
            APIResponseDTO apiResponse = new APIResponseDTO(
                    Objects.requireNonNull(updateResponse.getBody()).getStatus(),
                    updateResponse.getBody().getMessage(),
                    transactions
            );
            return ResponseEntity.ok(apiResponse);
        }
        return updateResponse;
    }
}
