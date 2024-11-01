package com.sforin.EtherscanTransactions.dto;

import com.sforin.EtherscanTransactions.enums.ResponseDTOStatus;

/**
 * A generic base class for standardizing API responses, encapsulating status, message, and result data.
 *
 * @param <T> the type of the response result (e.g., list of transactions, single object, etc.)
 */
public abstract class BaseResponseDTO<T> {
    private ResponseDTOStatus status;
    private String message;
    private T result;

    public BaseResponseDTO(ResponseDTOStatus status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public ResponseDTOStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseDTOStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
