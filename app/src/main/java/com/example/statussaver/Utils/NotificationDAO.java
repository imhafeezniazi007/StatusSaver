package com.example.statussaver.Utils;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.statussaver.Models.NotificationText;

@Dao
public interface NotificationDAO {
    @Insert
    void insert(NotificationText notificationText);
}
