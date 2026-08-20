package org.example.exam_prog2.models;

import java.math.BigDecimal;
import java.time.Instant;

public record Transaction(
        String id,
        Instant createdAt,
        TransactionType transactionType,
        BigDecimal amount,
        String reason,
        String accountId
) {
}
