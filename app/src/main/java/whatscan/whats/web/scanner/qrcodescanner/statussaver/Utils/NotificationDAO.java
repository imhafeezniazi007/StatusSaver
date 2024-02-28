package whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.NotificationText;

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
