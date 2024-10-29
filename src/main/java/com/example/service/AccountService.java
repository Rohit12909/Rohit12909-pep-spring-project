package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService 
{
    AccountRepository accountRepository;

    /**
     * 
     * @param accountRepository
     */
    @Autowired
    public AccountService(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
    }

    /**
     * Register a new account if the username does not already exist in the database,
     * the username is not blank, and if the password is >= to 4 characters
     * @param newAccount
     * @return newly registered account
     */
    public Account register(Account newAccount) 
    {
        Account usernameExists = accountRepository.checkUsernameExists(newAccount.getUsername());

        if (usernameExists == null)
        {
            if ((!newAccount.getUsername().isBlank()) && (newAccount.getPassword().length() >= 4))
            {   
                return accountRepository.save(newAccount);
            } 
        }
        else
        {
            return newAccount;
        }
       
        return null;
    }

    /**
     * Check if the username and password from the request are already in the database
     * @param newAccount
     * @return the already existing account
     */
    public Account login(Account existingAccount) 
    {
        Account usernameExists = accountRepository.checkUsernameExists(existingAccount.getUsername());
        Account passwordExists = accountRepository.loginAccount(existingAccount.getUsername(), existingAccount.getPassword());

        if (usernameExists != null)
        {
            if ( passwordExists != null)
            {
                existingAccount.setAccountId(passwordExists.getAccountId());
                return existingAccount;
            }
        }

        return null;
    }

    
}
