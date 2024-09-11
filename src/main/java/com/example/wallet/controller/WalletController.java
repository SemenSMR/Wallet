package com.example.wallet.controller;

import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.entity.Wallet;
import com.example.wallet.handler.InsufficientFundsException;
import com.example.wallet.handler.WalletNotFoundException;
import com.example.wallet.service.WalletService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/wallet")
public class WalletController {


    private WalletService walletService;

    @PostMapping
    public ResponseEntity<String> performOperation(@RequestBody WalletOperationRequest request) {
        try {
            walletService.performOperation(request);
            return ResponseEntity.ok("Operation Successful");
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
    @GetMapping("/{walletId}")
    public ResponseEntity<Wallet> getWalletBalance(@PathVariable UUID walletId){
        return ResponseEntity.ok(walletService.getWalletBalance(walletId));
    }
}
