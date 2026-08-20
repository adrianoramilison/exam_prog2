package org.example.exam_prog2.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exam_prog2.models.Account;
import org.example.exam_prog2.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;


    public Account getAccountById(String id) {
        log.info("Recherche du compte en banque : ID = {}", id);
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Désolé, aucun compte trouvé avec l'ID : " + id));
    }


    public List<Account> getAllAccounts() {
        log.info("Récupération de la liste complète des comptes");
        return accountRepository.findAll();
    }


    public Account createAccount(Account payload) {
        String accountId = (payload.id() != null && !payload.id().isBlank())
                ? payload.id()
                : UUID.randomUUID().toString();

        Account newAccount = new Account(accountId, payload.accountType(), List.of());
        log.info("Création d'un nouveau compte {} de type {}", newAccount.id(), newAccount.accountType());

        accountRepository.save(newAccount);
        return newAccount;
    }


    public boolean accountExists(String accountId) {
        return accountRepository.existsById(accountId);
    }
}


