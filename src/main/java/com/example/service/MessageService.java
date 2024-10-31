package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService 
{
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository)
    {
        this.messageRepository = messageRepository;
    }

    /**
     * Create message if messageText is not blank, not over 255 characters, 
     * and postedBy refers to a real, existing user
     * @param message
     * @return newly created message
     */
    public Message createMessage(Message message)
    {
        List<Message> allPostedBy = messageRepository.findAll();
        boolean userExists = false;

        for (Message m : allPostedBy)
        {
            if (m.getPostedBy().equals(message.getPostedBy()))
            {
                userExists = true;
                break;
            }
        }

        if (!message.getMessageText().isBlank() && message.getMessageText().length() <= 255 && userExists)
        {
            return messageRepository.save(message);
        }

        return null;
    }

    /**
     * 
     * @return a list of all messages
     */
    public List<Message> getAllMessages() 
    {
        return messageRepository.findAll();
    }

    /**
     * Get a message from the database using its messageId
     * @param messageId the messageId used to find a message in the database
     * @return message found using its messageId
     */
    public Message getMessageById(int messageId) 
    {
        return messageRepository.getMessageById(messageId);
    }

    /**
     * Delete messages found by its id
     * @param messageId the messageId used to find a message in the database
     * @return the number of messages/rows deleted that were found by their id 
     */
    @Transactional
    public int deleteMessageById(int messageId) 
    {
       return messageRepository.deleteMessageById(messageId);
    }

    /**
     * 
     * @param messageId
     * @return
     */
    @Transactional
    public int updateMessageById(String newMessage, int messageId) 
    {
        List<Message> allMessageIds = messageRepository.findAll();
        boolean idExists = false;

        for (Message m : allMessageIds)
        {
            if (m.getMessageId().equals(messageId))
            {
                idExists = true;
                break;
            }
        }

        if (!newMessage.isEmpty() && !newMessage.isBlank() && newMessage.length() <= 255 && idExists)
        {
            System.out.println("Message Length:" + newMessage.length());
            return messageRepository.updateMessageById(newMessage, messageId); // Message Successfully Updated
        }

        return -1; // Update was not successful
    }

    /**
     * Get a list of all messages posted by a particular user
     * @param accountId id used to find the user posting the messages
     * @return a list of all messages posted by the user with the matching accountId
     */
    public List<Message> getAllMessagesFromUser(int accountId) 
    {
        return messageRepository.getAllMessagesFromUser(accountId);
    }
}
