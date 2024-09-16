package com.example.wallet.service;

import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.dto.WalletResponse;

import java.util.UUID;

public interface WalletService {
    void performOperation(WalletOperationRequest request);
    WalletResponse getWalletBalance(UUID walletId);
}
