package org.example.exam_prog2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exam_prog2.models.Transaction;
import org.example.exam_prog2.models.TransactionType;
import org.example.exam_prog2.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> getTransactionsByType(String type) {
        log.info("Recuperation of the transaction of : {}", type);
        TransactionType transactionType = TransactionType.valueOf(type.toUpperCase());
        return transactionRepository.findByType(transactionType);
    }

    public List<Transaction> getAccountTransactions(String accountId) {
        log.info("Recuperation of historic: {}", accountId);
        return transactionRepository.findByAccountId(accountId);
    }

    @Transactional
    public Transaction createTransaction(Transaction payload) {
        log.info("Creation of a new transaction of : {}", payload.accountId());


        Transaction newTransaction = new Transaction(
                UUID.randomUUID().toString(),
                Instant.now(),
                payload.transactionType(),
                payload.amount(),
                payload.reason(),
                payload.accountId()
        );

        transactionRepository.save(newTransaction);
        return newTransaction;
    }

    public BigDecimal getAccountBalance(String accountId) {
        log.info("Calcul of the balance of  : {}", accountId);
        return transactionRepository.calculateBalanceByAccountId(accountId);
    }
}
