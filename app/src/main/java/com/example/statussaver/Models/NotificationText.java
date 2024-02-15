package com.example.statussaver.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notification_texts")
public class NotificationText {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;

    public NotificationText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }
}
