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
 
    @Query("FROM Account WHERE username = :usernameVar")
    Account checkUsernameExists(@Param("usernameVar") String username);
}
