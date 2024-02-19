package com.example.statussaver.Utils;


import static android.os.FileObserver.ALL_EVENTS;
import static android.os.FileObserver.DELETE;
import static android.os.FileObserver.DELETE_SELF;
import static android.os.FileObserver.MOVED_FROM;
import static android.os.FileObserver.MOVED_TO;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.FileObserver;
import android.os.Handler;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileObserverService extends NotificationListenerService {
    private RecursiveFileObserver fileObserver;
    private String targetDirectory;

    @Override
    public void onCreate() {
        super.onCreate();
        setupFileObserver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setupFileObserver();
        return START_STICKY;
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    String TAG = "de_ser";

    private void setupFileObserver() {
        targetDirectory = Consts.CPY_FILES_DIR;

        fileObserver = new RecursiveFileObserver(Consts.WHATSAPP_DIRECTORY, new RecursiveFileObserver.EventListener() {
            @Override
            public void onEvent(int event, File file) {
                if (file.exists()) {
                    Log.e("file_chk", "File: " + file);
                } else {
                    Log.e("file_chk", "File not found");
                }
                switch (event) {
                    case DELETE: {
                        Log.d(TAG, "onEvent: DELETE " + file.exists());
                        handleFileDelete(file);
                    }
                    break;

                    case MOVED_TO: {
                        Log.d(TAG, "onEvent: MOVED TO " + file.exists());
                        handleFileDelete(file);
                    }
                    break;
                    default:
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

        if (deletedFile.exists()) {
            try {
                String fileName = deletedFile.getName();

                File destDirectory;
                if (fileName.contains(".mp4")) {
                    destDirectory = new File(Consts.VID_FILE_DIR);
                } else if (fileName.contains(".jpg") || fileName.contains(".jpeg") || fileName.contains(".png") || fileName.contains(".webp")) {
                    destDirectory = new File(Consts.IMG_FILE_DIR);
                } else if (fileName.contains(".aac") || fileName.contains(".mp3")) {
                    destDirectory = new File(Consts.AUD_FILE_DIR);
                } else if (fileName.contains(".opus") || fileName.contains(".ogg")) {
                    destDirectory = new File(Consts.VOICE_FILE_DIR);
                } else {
                    destDirectory = new File(Consts.DOC_FILE_DIR);
                }

                if (!destDirectory.exists()) {
                    destDirectory.mkdirs();
                }

                File destFile = new File(destDirectory, fileName);

                copyFile(deletedFile, destFile);


            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Error copying file: " + e.getMessage());
            }
        }else {
            Log.e(TAG, "File does not exist anymore: " + deletedFile.getAbsolutePath());
        }
    }

            private void copyFile(File sourceFile, File destFile) throws IOException {

                try (FileInputStream fis = new FileInputStream(sourceFile);
                     FileOutputStream fos = new FileOutputStream(destFile);
                     FileChannel source = fis.getChannel();
                     FileChannel destination = fos.getChannel()) {
                    destination.transferFrom(source, 0, source.size());
                }
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(Uri.fromFile(destFile));
                sendBroadcast(mediaScanIntent);
            }

    }
