package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) throws IllegalArgumentException {
        if(account.getUsername() == null || account.getUsername().isBlank()) {
            throw new IllegalArgumentException();
        }
        if(account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException();
        }

        //Check if username exists
        if(accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        return accountRepository.save(account);
    }

    public Account login(String username, String password) throws IllegalArgumentException {
        // Find account by username
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        // Validate password
        if (!account.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return account;
    }
}
