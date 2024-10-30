package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;

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
}
