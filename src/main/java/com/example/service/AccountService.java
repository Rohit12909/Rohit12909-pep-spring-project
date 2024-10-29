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
     * 
     * @param newAccount
     * @return
     */
    public Account register(Account newAccount) 
    {
        if (!AccountExists(newAccount))
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
     * Check if an account exists in the database
     * @param account to search for in the database
     */
    public boolean AccountExists(Account account)
    {
        List<Account> allAccounts = accountRepository.findAll();
        boolean exists = false;

        for (Account a : allAccounts)
        {
            if (account.getUsername().equals(a.getUsername()))
            {
                exists = true;
                break;
            }
        }

        return exists;
    }

    
}
