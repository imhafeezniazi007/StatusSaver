package com.example.statussaver.Utils;


import java.io.File;

public class ItemCounter {

    public int countItems(String path) {
        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) {
            return 0;
        }

        int count = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                count++;
                if (file.isDirectory()) {
                    count += countItems(file.getAbsolutePath());
                }
            }
        }
        return count;
    }
}

