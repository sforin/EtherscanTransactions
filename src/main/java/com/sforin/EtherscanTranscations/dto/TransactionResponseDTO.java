package com.sforin.EtherscanTranscations.dto;

import java.math.BigDecimal;

/**
 *
 */
public class TransactionResponseDTO {
    private String address;
    private Integer blockNumber;
    private Integer timeStamp;
    private String hash;
    private String from;
    private String to;
    private BigDecimal value;
    private Integer gasUsed;

    public TransactionResponseDTO(String address, Integer blockNumber, Integer timeStamp, String hash, String from, String to, BigDecimal value, Integer gasUsed) {
        this.address = address;
        this.blockNumber = blockNumber;
        this.timeStamp = timeStamp;
        this.hash = hash;
        this.from = from;
        this.to = to;
        this.value = value;
        this.gasUsed = gasUsed;
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
}
