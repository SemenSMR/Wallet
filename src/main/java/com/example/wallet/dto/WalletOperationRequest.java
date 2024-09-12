package com.example.wallet.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


public class WalletOperationRequest {
    private UUID walletId;
    private OperationType operationType;
    private BigDecimal amount;

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }



}

