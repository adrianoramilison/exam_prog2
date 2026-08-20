package org.example.exam_prog2.controllers;


import lombok.RequiredArgsConstructor;
import org.example.exam_prog2.models.Transaction;
import org.example.exam_prog2.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@RequestParam String type) {
        return ResponseEntity.ok(transactionService.getTransactionsByType(type));
    }


    @PostMapping("/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.status(201).body(savedTransaction);
    }
}
