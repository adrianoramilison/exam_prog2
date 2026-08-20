package org.example.exam_prog2.controllers;


import lombok.RequiredArgsConstructor;
import org.example.exam_prog2.models.Transaction;
import org.example.exam_prog2.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final TransactionService transactionService;


    @GetMapping("/accounts/{id}/transactions")
    public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable String id) {
        return ResponseEntity.ok(transactionService.getAccountTransactions(id));
    }


    @GetMapping("/accounts/{id}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable String id) {
        return ResponseEntity.ok(transactionService.getAccountBalance(id));
    }
}