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

                try {
                    if (deletedFile.getName().contains(".mp4")) {
                        File videoDirectory = new File(Consts.VID_FILE_DIR);
                        if (!videoDirectory.exists()) {
                            videoDirectory.mkdirs();
                        }
                        File destFile = new File(videoDirectory, deletedFile.getName());

                        copyFile(deletedFile, destFile);
                    } else if (deletedFile.getName().contains(".jpg") || deletedFile.getName().contains(".jpeg") || deletedFile.getName().contains(".png") || deletedFile.getName().contains(".webp")) {
                        File imageDirectory = new File(Consts.IMG_FILE_DIR);
                        if (!imageDirectory.exists()) {
                            imageDirectory.mkdirs();
                        }
                        File destFile = new File(imageDirectory, deletedFile.getName());

                        copyFile(deletedFile, destFile);
                    } else if (deletedFile.getName().contains(".aac") || deletedFile.getName().contains(".mp3")) {
                        File audioDirectory = new File(Consts.AUD_FILE_DIR);
                        if (!audioDirectory.exists()) {
                            audioDirectory.mkdirs();
                        }
                        File destFile = new File(audioDirectory, deletedFile.getName());

                        copyFile(deletedFile, destFile);
                    } else if (deletedFile.getName().contains(".opus") || deletedFile.getName().contains(".ogg")) {
                        File voiceDirectory = new File(Consts.VOICE_FILE_DIR);
                        if (!voiceDirectory.exists()) {
                            voiceDirectory.mkdirs();
                        }
                        File destFile = new File(voiceDirectory, deletedFile.getName());

                        copyFile(deletedFile, destFile);
                    } else {
                        File docDirectory = new File(Consts.DOC_FILE_DIR);
                        if (!docDirectory.exists()) {
                            docDirectory.mkdirs();
                        }
                        File destFile = new File(docDirectory, deletedFile.getName());

                        copyFile(deletedFile, destFile);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Error copying file: " + e.getMessage());
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
