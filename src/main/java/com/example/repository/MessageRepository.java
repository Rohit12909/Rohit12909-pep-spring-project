package com.example.repository;

import com.example.entity.Message;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository  extends JpaRepository<Message, Long>
{
    /**
     * Get a message from the database using its messageId
     * @param id the messageId used to find a message in the database
     * @return message found using its messageId
     */
    @Query("FROM Message WHERE messageId = :id")
    Message getMessageById(@Param("id") int id);

    /**
     * Delete messages found by its id
     * @param id the messageId used to find a message in the database
     * @return the number of messages/rows deleted that were found by their id 
     */
    @Modifying
    @Query("DELETE FROM Message WHERE messageId = :id")
    int deleteMessageById(@Param("id") int id);

    /**
     * Delete messages found by its id
     * @param id the messageId used to find a message in the database
     * @return the number of messages/rows deleted that were found by their id 
     */
    @Modifying
    @Query("UPDATE Message SET messageText = :newMessage WHERE messageId = :id")
    int updateMessageById(@Param("newMessage") String newMessage, @Param("id") int id);

    /**
     * 
     * @param accountId
     * @return
     */
    @Query("FROM Message WHERE postedBy = :accountId")
    List<Message> getAllMessagesFromUser(@Param("accountId") int accountId);


}
