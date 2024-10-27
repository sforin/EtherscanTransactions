package com.sforin.EtherscanTranscations.dto;

import com.sforin.EtherscanTranscations.enums.ResponseDTOStatus;

import java.util.List;

/**
 *
 */
public class APIResponseDTO extends BaseResponseDTO<List<TransactionResponseDTO>> {
    public APIResponseDTO(ResponseDTOStatus status, String message, List<TransactionResponseDTO> response) {
        super(status, message, response);
    }
}
