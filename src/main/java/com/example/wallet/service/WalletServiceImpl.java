package com.example.wallet.service;

import com.example.wallet.dto.OperationType;
import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.dto.WalletResponse;
import com.example.wallet.entity.Wallet;
import com.example.wallet.handler.InsufficientFundsException;
import com.example.wallet.handler.WalletNotFoundException;
import com.example.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service

public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Transactional
    public void performOperation(WalletOperationRequest request) {
        UUID walletId = request.getWalletId();
        BigDecimal amount = request.getAmount();

        Optional<Wallet> optionalWallet = walletRepository.findByIdForUpdate(walletId);
        if (optionalWallet.isEmpty()) {
            throw new WalletNotFoundException("Wallet not found");
        }
        Wallet wallet = optionalWallet.get();

        if (request.getOperationType() == OperationType.WITHDRAW) {
            if (wallet.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds");
            }
            amount = amount.negate();
        } else if (request.getOperationType() == OperationType.DEPOSIT) {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Deposit amount must be greater than zero");
            }
        } else {
            throw new IllegalArgumentException("Invalid operation type");
        }
        int updated = walletRepository.updateBalance(walletId, amount);
        if (updated == 0) {
            throw new RuntimeException("Failed to update wallet balance");
        }
    }

    public WalletResponse getWalletBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
        if (wallet.getBalance() == null) {
            throw new IllegalStateException("Balance is null");
        }
        return new WalletResponse(wallet.getId().toString(), wallet.getBalance());
    }

}
