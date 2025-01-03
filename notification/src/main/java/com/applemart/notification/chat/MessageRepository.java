package com.applemart.notification.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoomIdOrderByTimestampAsc(Long chatRoomId);

    @Query("SELECT m FROM Message m WHERE (m.senderId = : senderId AND m.receiverId = :receiverId) OR (m.senderId = :receiverId AND m.receiverId = :senderId) ORDER BY m.timestamp ASC")
    List<Message> findBySenderIdAndReceiverIdOrderByTimestamp(@Param("senderId") Integer senderId, @Param("receiverId") Integer receiverId);
}
