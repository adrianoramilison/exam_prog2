package org.example.exam_prog2.repository;

import lombok.RequiredArgsConstructor;
import org.example.exam_prog2.models.Account;
import org.example.exam_prog2.models.AccountType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

    private final JdbcTemplate jdbcTemplate;


    private final RowMapper<Account> accountRowMapper = (rs, rowNum) -> new Account(
            rs.getString("id"),
            AccountType.valueOf(rs.getString("account_type")),
            List.of()
    );



    public Optional<Account> findById(String id) {
        String sql = "SELECT * FROM account WHERE id = ?";
        List<Account> results = jdbcTemplate.query(sql, accountRowMapper, id);
        return results.stream().findFirst();
    }


    public List<Account> findAll() {
        String sql = "SELECT * FROM account";
        return jdbcTemplate.query(sql, accountRowMapper);
    }


    public void save(Account account) {
        String sql = "INSERT INTO account (id, account_type) VALUES (?, ?)";
        jdbcTemplate.update(sql, account.id(), account.accountType().name());
    }


    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM account WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
