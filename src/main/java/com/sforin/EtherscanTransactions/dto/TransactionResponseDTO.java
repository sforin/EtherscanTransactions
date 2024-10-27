package com.sforin.EtherscanTransactions.dto;

import java.math.BigDecimal;
import java.math.BigInteger;


public class TransactionResponseDTO {
    private String address;
    private Integer blockNumber;
    private Integer timeStamp;
    private String hash;
    private String from;
    private String to;
    private BigDecimal value;
    private Integer gasUsed;
    private BigInteger balance;

    /**
     * Constructs a new instance of TransactionResponseDTO with the specified details
     * of a transaction.
     * <p>
     * This constructor initializes a TransactionResponseDTO object, encapsulating
     * all relevant information regarding an Ethereum transaction, including the
     * sender and receiver addresses, transaction value, gas used, and the balance
     * at the time of the transaction.
     *
     * @param address The Ethereum address associated with the transaction. This
     *                represents the address for which the transaction details are
     *                being provided.
     * @param blockNumber The block number in which the transaction was included,
     *                    represented as an Integer.
     * @param timeStamp The timestamp of the transaction, represented as an
     *                  Integer (Unix epoch time).
     * @param hash The unique transaction hash, which acts as an identifier for
     *             this specific transaction.
     * @param from The address of the sender of the transaction.
     * @param to The address of the recipient of the transaction.
     * @param value The amount of Ether transferred in the transaction, represented
     *               as a BigDecimal.
     * @param gasUsed The amount of gas used for the transaction, represented as an
     *                 Integer.
     * @param balance The balance of the address after the transaction, represented
     *                as a BigInteger.
     */
    public TransactionResponseDTO(String address, Integer blockNumber, Integer timeStamp, String hash, String from, String to, BigDecimal value, Integer gasUsed, BigInteger balance) {
        this.address = address;
        this.blockNumber = blockNumber;
        this.timeStamp = timeStamp;
        this.hash = hash;
        this.from = from;
        this.to = to;
        this.value = value;
        this.gasUsed = gasUsed;
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Integer getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Integer timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(Integer gasUsed) {
        this.gasUsed = gasUsed;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }
}
