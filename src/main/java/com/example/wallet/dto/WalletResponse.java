package com.example.wallet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class WalletResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("balance")
    private BigDecimal balance;

    public WalletResponse(String id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
