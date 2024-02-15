package com.example.statussaver.Utils;


import static android.os.FileObserver.DELETE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.FileObserver;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.statussaver.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileObserverService extends Service {
    private RecursiveFileObserver fileObserver;
    private String targetDirectory;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, createNotification());
        setupFileObserver();
        return START_STICKY;
    }


    private Notification createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "1",
                    "Channel One",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setContentTitle("File Observer Service")
                .setContentText("Watching files...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        return builder.build();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
String TAG="de_ser";
    private void setupFileObserver() {
        targetDirectory = Consts.CPY_FILES_DIR;
        fileObserver = new RecursiveFileObserver(Consts.WHATSAPP_DIRECTORY, new RecursiveFileObserver.EventListener() {
            @Override
            public void onEvent(int event, File file) {
                Log.e(TAG, "onEvent: "+event);
                switch (event) {
                    case DELETE:
                        handleFileDelete(file);
                        break;
                }
            }
        });

        fileObserver.startWatching();
    }


    private void handleFileDelete(File deletedFile) {
        File fileDirectory = new File(Consts.CPY_FILES_DIR);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }

        File destFile = new File(fileDirectory, deletedFile.getName());

        try {
            copyFile(Consts.WHATSAPP_DIRECTORY+"/WhatsApp Images/Sent/IMG-20191101-WA0009.jpg", destFile);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error copying file: " + e.getMessage());
        }


        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(destFile));
        sendBroadcast(mediaScanIntent);
    }

    private void copyFile(String sourceFile, File destFile) throws IOException {

        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(destFile);
             FileChannel source = fis.getChannel();
             FileChannel destination = fos.getChannel()) {
             destination.transferFrom(source, 0, source.size());

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        fileObserver.stopWatching();
    }
}
