package com.example.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController 
{
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService)
    {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Register a new account if the username does not already exist in the database,
     * the username is not blank, and if the password is >= to 4 characters
     * @param newAccount JSON of an Account
     * @return JSON of newly registered account and status 200, 
     *         if duplicate username return status 409, 
     *         else return staus 400
     */
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account newAccount)
    {
        Account registered = accountService.register(newAccount);

        if (registered != null)
        {
            if (registered.getAccountId() == null) // newAccount had a duplicate username so a new ID was never generated
            {
                return ResponseEntity.status(409).body(newAccount); // Conflict
            }

            return ResponseEntity.status(HttpStatus.OK) // register successful
                .body(registered);
        }

        return ResponseEntity.status(400).body(null); // Client Error
    }

    /**
     * Login will be successful if and only if the username and password provided in the request body JSON 
     * match an account in the database
     * @param existingAccount JSON of an Account
     * @return the account with a successful login with a status 200,
     *         else return status 401
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account existingAccount)
    {
        Account registered = accountService.login(existingAccount);

        if (registered != null) 
        {
            return ResponseEntity.status(HttpStatus.OK).body(registered); // login successful
        }
        
        return ResponseEntity.status(401).body(null); // login unauthorized

    }
    
    /**
     * Create message if messageText is not blank, not over 255 characters, 
     * and postedBy refers to a real, existing user
     * 
     * @param newMessage JSON of an Message
     * @return the message with a successful login with a status 200,
     *         else return status 400
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage)
    {
        Message created = messageService.createMessage(newMessage);

        if (created != null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(created); // Create Message Successful
        }

        return ResponseEntity.status(400).body(null); // Client Error
    }

    /**
     * Get a list of all messages from the database
     * @return a list of all messages and status 200
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages()
    {
        List<Message> allMessages = messageService.getAllMessages();

        return ResponseEntity.status(HttpStatus.OK).body(allMessages);
    }

    /**
     * Get a message from the database using its messageId
     * @param messageId the messageId used to find a message in the database
     * @return message found using its messageId and status 200
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId)
    {
        Message message = messageService.getMessageById(messageId);

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /**
     * Delete messages found by its id
     * @param messageId the messageId used to find a message in the database
     * @return the number of messages/rows deleted that were found by their id and return status 200
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId)
    {
        int rowsDeleted = messageService.deleteMessageById(messageId);

        if (rowsDeleted > 0)
        {
            return ResponseEntity.status(HttpStatus.OK).body(rowsDeleted);
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    
}
