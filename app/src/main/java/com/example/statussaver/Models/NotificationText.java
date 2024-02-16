// NotificationText.java
package com.example.statussaver.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notification_texts")
public class NotificationText {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String senderName;
    private String text;
    private long timestamp;

    public NotificationText(String senderName, String text, long timestamp) {
        this.senderName = senderName;
        this.text = text;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}