package com.sforin.EtherscanTransactions.dto;

import com.sforin.EtherscanTransactions.enums.ResponseDTOStatus;
import com.sforin.EtherscanTransactions.model.Transaction;

import java.util.List;


public class EtherscanResponseDTO extends BaseResponseDTO<List<Transaction>> {
    /**
     * Constructs a new `EtherscanResponseDTO` with the specified status, message, and list of transactions.
     *
     * @param status the status of the API response (e.g., OK, ERROR, etc.)
     * @param message a descriptive message detailing the response
     * @param result the list of transactions included in the response
     */
    public EtherscanResponseDTO(ResponseDTOStatus status, String message, List<Transaction> result) {
        super(status, message, result);
    }
}
