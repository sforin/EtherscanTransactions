package com.sforin.EtherscanTranscations.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "\"address\"")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "address", nullable = false, length = 42)
    private String address;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "last_update_at", nullable = false)
    private Timestamp lastUpdateAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(Timestamp lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

}