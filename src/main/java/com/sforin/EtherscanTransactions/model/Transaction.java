package com.sforin.EtherscanTransactions.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "\"transaction\"")
public class Transaction {

    @Id
    @Column(name = "transaction_hash", nullable = false, length = 66)
    private String hash;

    @Column(name = "block_number", nullable = false)
    private Integer blockNumber;

    @Column(name = "time_stamp", nullable = false)
    private Integer timeStamp;

    @Column(name = "\"from\"", nullable = false, length = 42)
    private String from;

    @Column(name = "\"to\"", nullable = false, length = 42)
    private String to;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "gas_used", nullable = false)
    private Integer gasUsed;

    @JoinColumn(name = "address", nullable = false)
    private int address;

    public String getHash() {
        return hash;
    }

    public void setHash(String transactionHash) {
        this.hash = transactionHash;
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

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionHash='" + hash + '\'' +
                ", blockNumber=" + blockNumber +
                ", timeStamp=" + timeStamp +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", value=" + value +
                ", gasUsed=" + gasUsed +
                ", address=" + address +
                '}';
    }

}