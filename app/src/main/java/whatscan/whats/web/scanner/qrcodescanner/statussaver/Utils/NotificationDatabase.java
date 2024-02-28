package whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.NotificationText;

@Database(entities = {NotificationText.class}, version = 2, exportSchema = false)
public abstract class NotificationDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "notification_db";
    private static NotificationDatabase instance;

    public abstract NotificationDAO notificationDao();

    public static synchronized NotificationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NotificationDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}