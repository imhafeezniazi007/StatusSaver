package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.statussaver.Adapters.ImageAdapter;
import com.example.statussaver.Adapters.QRScanAdapter;
import com.example.statussaver.Adapters.SavedStatusAdapter;
import com.example.statussaver.Fragments.ImageFragment;
import com.example.statussaver.Models.MediaFile;
import com.example.statussaver.Models.Status;
import com.example.statussaver.R;
import com.example.statussaver.Utils.Consts;
import com.example.statussaver.databinding.ActivityMainFeaturesBinding;
import com.example.statussaver.databinding.ActivitySavedStatusBinding;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SavedStatusActivity extends AppCompatActivity {

    ActivitySavedStatusBinding activitySavedStatusBinding;
    ArrayList<MediaFile> arrayList;
    SavedStatusAdapter savedStatusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activitySavedStatusBinding = ActivitySavedStatusBinding.inflate(getLayoutInflater());
        setContentView(activitySavedStatusBinding.getRoot());
        activitySavedStatusBinding.toolbar.setTitle("Saved Statuses");
        setSupportActionBar(activitySavedStatusBinding.toolbar);


        arrayList = new ArrayList<>();
        savedStatusAdapter = new SavedStatusAdapter(arrayList);
        activitySavedStatusBinding.rvSavedStatuses.setAdapter(savedStatusAdapter);
        activitySavedStatusBinding.rvSavedStatuses.setLayoutManager(new GridLayoutManager(this, 3));

        loadMediaFilesFromFolder();


    }

    private void loadMediaFilesFromFolder() {
                File[] mediaFiles = Consts.APP_DIR_SAVED.listFiles();

                if (mediaFiles != null && mediaFiles.length > 0) {
                    Arrays.sort(mediaFiles);

                    for (final File mediaFile : mediaFiles) {
                        MediaFile media = new MediaFile(mediaFile, mediaFile.getName(),
                                mediaFile.getAbsolutePath());

                        media.setThumbnail(getThumbnail(media));

                        arrayList.add(media);
                    }
                }
                else {
                    Log.e("my_tag","No files");
                }
            }
    public Bitmap getThumbnail(MediaFile file) {
        if (file.isVideo()) {
            return ThumbnailUtils.createVideoThumbnail(file.getFile().getAbsolutePath(),
                    MediaStore.Video.Thumbnails.MICRO_KIND);
        } else {
            return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getFile().getAbsolutePath())
                    , Consts.THUMBSIZE
                    , Consts.THUMBSIZE);
        }
    }
}
