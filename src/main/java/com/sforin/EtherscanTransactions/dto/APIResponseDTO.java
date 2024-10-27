package com.sforin.EtherscanTransactions.dto;

import com.sforin.EtherscanTransactions.enums.ResponseDTOStatus;

import java.util.List;

public class APIResponseDTO extends BaseResponseDTO<List<TransactionResponseDTO>> {
    /**
     * Constructs an APIResponseDTO to encapsulate a standardized API response.
     *
     * @param status   the response status indicating the outcome of the operation (e.g., OK, ERROR).
     * @param message  a descriptive message providing additional details about the response.
     * @param response a list of TransactionResponseDTO objects representing the transaction data associated with the response.
     */

    public APIResponseDTO(ResponseDTOStatus status, String message, List<TransactionResponseDTO> response) {
        super(status, message, response);
    }
}
