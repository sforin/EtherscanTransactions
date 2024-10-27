package com.sforin.EtherscanTransactions.enums;

public enum LogOperation {
    NEW_ADDRESS("Inserted a new address"),
    UPDATE_ADDRESS("Address updated"),
    TRANSACTION_DOWNLOAD("Downloaded a new transactions");

    private final String description;

    public String getDescription() {
        return description;
    }

    private LogOperation(String description) {
        this.description = description;
    }

}
