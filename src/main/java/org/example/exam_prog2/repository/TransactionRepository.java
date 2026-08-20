package org.example.exam_prog2.repository;


import lombok.RequiredArgsConstructor;
import org.example.exam_prog2.models.Transaction;
import org.example.exam_prog2.models.TransactionType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;


    private final RowMapper<Transaction> transactionRowMapper = (rs, rowNum) -> new Transaction(
            rs.getString("id"),
            rs.getTimestamp("created_at").toInstant(),
            TransactionType.valueOf(rs.getString("transaction_type")),
            rs.getBigDecimal("amount"),
            rs.getString("reason"),
            rs.getString("account_id")
    );

    public List<Transaction> findByType(TransactionType type) {
        String sql = "SELECT * FROM transaction WHERE transaction_type = ?";
        return jdbcTemplate.query(sql, transactionRowMapper, type.name());
    }

    public List<Transaction> findByAccountId(String accountId) {
        String sql = "SELECT * FROM transaction WHERE account_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, transactionRowMapper, accountId);
    }

    public void save(Transaction transaction) {
        String sql = """
            INSERT INTO transaction (id, created_at, transaction_type, amount, reason, account_id)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        jdbcTemplate.update(sql,
                transaction.id(),
                Timestamp.from(transaction.createdAt()),
                transaction.transactionType().name(),
                transaction.amount(),
                transaction.reason(),
                transaction.accountId()
        );
    }


    public BigDecimal calculateBalanceByAccountId(String accountId) {
        String sql = """
            SELECT COALESCE(SUM(
                CASE WHEN transaction_type = 'IN' THEN amount
                     WHEN transaction_type = 'OUT' THEN -amount
                     ELSE 0 END
            ), 0) FROM transaction WHERE account_id = ?
            """;
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);
    }
}
