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
}
