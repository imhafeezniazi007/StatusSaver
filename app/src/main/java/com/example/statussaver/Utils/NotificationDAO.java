package com.example.statussaver.Utils;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.statussaver.Models.NotificationText;

import java.util.List;

@Dao
public interface NotificationDAO {
    @Insert
    void insert(NotificationText notificationText);

    @Query("SELECT DISTINCT senderName FROM notification_texts")
    List<String> getAllSenderNames();

    @Query("SELECT * FROM notification_texts WHERE senderName = :senderName")
    List<NotificationText> getAllMessagesForSender(String senderName);
    @Query("SELECT COUNT(DISTINCT senderName) FROM notification_texts")
    int getTotalSenders();

    @Query("SELECT * FROM notification_texts WHERE senderName = :senderName ORDER BY timestamp DESC LIMIT 1")
    NotificationText getLatestMessageForSender(String senderName);

    @Query("DELETE FROM notification_texts")
    void deleteAllNotifications();
}
