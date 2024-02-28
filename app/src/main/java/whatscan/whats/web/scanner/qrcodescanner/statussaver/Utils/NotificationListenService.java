package whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.NotificationText;

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
            String senderName = sbn.getNotification().extras.getString(Notification.EXTRA_TITLE);
            long timestamp = sbn.getPostTime();
            if (notificationText != null && senderName != null) {
                Log.e("_serv", "onNotificationPosted: " + notificationText);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NotificationDatabase.getInstance(getApplicationContext()).notificationDao().insert(new NotificationText(senderName, notificationText, timestamp));
                    }
                }).start();
            }
        }
    }
}