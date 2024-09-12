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
import java.util.UUID;

@Service

public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Transactional
    public void performOperation(WalletOperationRequest request) {
        Wallet wallet = walletRepository.findById(request.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        synchronized (wallet) {
            if (request.getOperationType() == OperationType.WITHDRAW) {
                if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
                    throw new InsufficientFundsException("Insufficient found");

                }
                wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));
            } else if (request.getOperationType() == OperationType.DEPOSIT) {
                if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("Deposit amount must be greater than zero");
                }
                wallet.setBalance(wallet.getBalance().add(request.getAmount()));
            }
            walletRepository.save(wallet);
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
