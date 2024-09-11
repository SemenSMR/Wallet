package com.example.wallet.service;

import com.example.wallet.dto.OperationType;
import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.entity.Wallet;
import com.example.wallet.handler.InsufficientFundsException;
import com.example.wallet.handler.WalletNotFoundException;
import com.example.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletService {

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
                wallet.setBalance(wallet.getBalance().add(request.getAmount()));
            }
            walletRepository.save(wallet);
        }
    }

    public Wallet getWalletBalance(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
    }

}
