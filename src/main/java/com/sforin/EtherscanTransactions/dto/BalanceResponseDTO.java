package com.sforin.EtherscanTransactions.dto;

import com.sforin.EtherscanTransactions.enums.ResponseDTOStatus;

import java.math.BigInteger;

public class BalanceResponseDTO extends BaseResponseDTO<BigInteger> {
    /**
     * Constructs a new instance of BalanceResponseDTO with the specified status,
     * message, and balance.
     * <p>
     * This constructor initializes a BalanceResponseDTO object, allowing clients
     * to create a response that includes the status of the request, a descriptive
     * message, and the current balance represented as a BigInteger.
     *
     * @param status The status of the response, indicating the result of the
     *               operation (e.g., success, failure). It should be one of the
     *               values from the ResponseDTOStatus enumeration.
     * @param message A message providing additional context about the response,
     *                which can help clients understand the result of the request.
     * @param balance The current balance as a BigInteger. This value represents
     *                the Ethereum balance associated with an address and is
     *                returned as part of the response.
     */
    public BalanceResponseDTO(ResponseDTOStatus status, String message, BigInteger balance) {
        super(status, message, balance);
    }
}
