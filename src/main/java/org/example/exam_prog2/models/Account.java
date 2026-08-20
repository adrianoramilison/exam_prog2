package org.example.exam_prog2.models;

import java.util.List;

public record Account(
        String id,
        AccountType accountType,
        List<Transaction> transactions
) {
}
