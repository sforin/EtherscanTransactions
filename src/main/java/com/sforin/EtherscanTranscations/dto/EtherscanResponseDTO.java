package com.sforin.EtherscanTranscations.dto;

import com.sforin.EtherscanTranscations.enums.ResponseDTOStatus;
import com.sforin.EtherscanTranscations.model.Transaction;

import java.util.List;

/**
 *
 */
public class EtherscanResponseDTO extends BaseResponseDTO<List<Transaction>> {

    public EtherscanResponseDTO(ResponseDTOStatus status, String message, List<Transaction> result) {
        super(status, message, result);
    }
}
