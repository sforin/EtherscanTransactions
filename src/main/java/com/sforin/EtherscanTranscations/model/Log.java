package com.sforin.EtherscanTranscations.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "\"timestamp\"", nullable = false)
    private Timestamp timestamp;

    @Column(name = "operation", nullable = false, length = Integer.MAX_VALUE)
    private String operation;

    @Column(name = "additional_info", length = Integer.MAX_VALUE)
    private String additionalInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

}