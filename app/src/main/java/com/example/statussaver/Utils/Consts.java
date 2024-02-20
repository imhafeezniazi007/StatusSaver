package com.example.statussaver.Utils;

import android.os.Environment;

import java.io.File;

public class Consts {
    public static final File STATUS_DIRECTORY = new File(Environment.getExternalStorageDirectory()
            + File.separator + "WhatsApp/Media/.Statuses");
 public static final String WHATSAPP_DIRECTORY = Environment.getExternalStorageDirectory()
            + File.separator + "WhatsApp/Media";
 public static final String WHATSAPP_IMG_DIRECTORY = Environment.getExternalStorageDirectory()
            + File.separator + "WhatsApp/Media/WhatsApp Images/Sent";

    public static final File STATUS_DIRECTORY_BUSINESS = new File(Environment.getExternalStorageDirectory()
            + File.separator + "WhatsApp Business/Media/.Statuses");

    public static final String APP_DIR = Environment.getExternalStorageDirectory()
            + File.separator + "StatusSaverDir";
    public static final String CPY_FILES_DIR = Environment.getExternalStorageDirectory()
            + File.separator + "WhatsWebCpyFiles";
    public static final String VID_FILE_DIR = Environment.getExternalStorageDirectory()
            + File.separator + "WhatsWebCpyFiles/Videos";
    public static final String IMG_FILE_DIR = Environment.getExternalStorageDirectory()
            + File.separator + "WhatsWebCpyFiles/Images";
    public static final String AUD_FILE_DIR = Environment.getExternalStorageDirectory()
            + File.separator + "WhatsWebCpyFiles/Audios";
    public static final String DOC_FILE_DIR = Environment.getExternalStorageDirectory()
            + File.separator + "WhatsWebCpyFiles/Documents";
    public static final String VOICE_FILE_DIR = Environment.getExternalStorageDirectory()
            + File.separator + "WhatsWebCpyFiles/Voices";
    public static final String APP_DIR_BUSINESS = Environment.getExternalStorageDirectory()
            + File.separator + "BusinessStatusSaverDir";

    public static final File APP_DIR_SAVED = new File(Environment.getExternalStorageDirectory()
            + File.separator + "StatusSaverDir");
    public static final File REC_IMG_DIR = new File(Environment.getExternalStorageDirectory()
            + File.separator + "WhatsWebCpyFiles/Images");
    public static final File REC_VID_DIR = new File(Environment.getExternalStorageDirectory()
            + File.separator + "WhatsWebCpyFiles/Videos");
    public static final File REC_AUD_DIR = new File(Environment.getExternalStorageDirectory()
            + File.separator + "WhatsWebCpyFiles/Audios");
    public static final File REC_DOC_DIR = new File(Environment.getExternalStorageDirectory()
            + File.separator + "WhatsWebCpyFiles/Documents");
    public static final File REC_VOICE_DIR = new File(Environment.getExternalStorageDirectory()
            + File.separator + "WhatsWebCpyFiles/Voices");

    public static final int THUMBSIZE = 128;
}
