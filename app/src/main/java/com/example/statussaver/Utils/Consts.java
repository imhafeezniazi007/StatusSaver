package com.example.statussaver.Utils;

import android.os.Environment;

import java.io.File;

public class Consts {
    public static final File STATUS_DIRECTORY = new File(Environment.getExternalStorageDirectory()
            + File.separator + "WhatsApp/Media/.Statuses");

    public static final File STATUS_DIRECTORY_BUSINESS = new File(Environment.getExternalStorageDirectory()
            + File.separator + "WhatsApp Business/Media/.Statuses");

    public static final String APP_DIR = Environment.getExternalStorageDirectory()
            + File.separator + "StatusSaverDir";
    public static final String APP_DIR_BUSINESS = Environment.getExternalStorageDirectory()
            + File.separator + "BusinessStatusSaverDir";

    public static final File APP_DIR_SAVED = new File(Environment.getExternalStorageDirectory()
            + File.separator + "StatusSaverDir");

    public static final int THUMBSIZE = 128;
}
