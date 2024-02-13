package com.example.statussaver.Models;

import android.graphics.Bitmap;

import java.io.File;

public class MediaFile {
    public static final String MP4 = ".mp4";
    private String name;
    private String path;
    private Bitmap thumbnail;
    private final File file;
    private boolean isVideo;

    public MediaFile(File file, String name, String path) {
        this.file = file;
        this.name = name;
        this.path = path;
        this.isVideo = file.getName().endsWith(MP4);
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public String getPath() {
        return path;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }
}
