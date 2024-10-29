package com.example.repository;

import com.example.entity.Account;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>
{
 
    /**
     * Check if an account with the username exists in the database
     * @param username
     * @return account with matching username
     */
    @Query("FROM Account WHERE username = :usernameVar")
    Account checkUsernameExists(@Param("usernameVar") String username);

    /**
     * Check if an account with the username and password exist in the database
     * @param username
     * @param password
     * @return account with matching username and password
     */
    @Query("FROM Account WHERE username = :usernameVar AND password = :passwordVar")
    Account loginAccount(@Param("usernameVar") String username, @Param("passwordVar") String password);
}
