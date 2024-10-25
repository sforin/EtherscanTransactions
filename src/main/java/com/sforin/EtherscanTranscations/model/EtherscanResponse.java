package com.sforin.EtherscanTranscations.model;

import java.util.List;

public class EtherscanResponse {

    private String status;
    private String message;
    private List<Transaction> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Transaction> getResult() {
        return result;
    }

    public void setResult(List<Transaction> result) {
        this.result = result;
    }
}
