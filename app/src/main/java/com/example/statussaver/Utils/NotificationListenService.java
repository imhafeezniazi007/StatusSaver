package com.example.statussaver.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.statussaver.Models.NotificationText;

public class NotificationListenService extends NotificationListenerService {
    private NotificationDatabase notificationDatabase;
    private NotificationManager notificationManager;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn.getPackageName().equals("com.whatsapp")) {
            String notificationText = sbn.getNotification().extras.getString(Notification.EXTRA_TEXT);
            if (notificationText != null) {
                Log.e("_serv", "onNotificationPosted: " + notificationText);
                // Store the notification text in Room database
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Store the notification text in Room database
                        NotificationDatabase.getInstance(getApplicationContext()).notificationDao().insert(new NotificationText(notificationText));
                    }
                }).start();
            }

        }
    }

}