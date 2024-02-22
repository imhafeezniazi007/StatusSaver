package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.statussaver.Adapters.RecoveredImageAdapter;
import com.example.statussaver.Adapters.SavedImageAdapter;
import com.example.statussaver.Fragments.SavedImageFragment;
import com.example.statussaver.Models.MediaFile;
import com.example.statussaver.R;
import com.example.statussaver.Utils.Consts;
import com.example.statussaver.databinding.ActivityImageBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ImageActivity extends AppCompatActivity {

    ActivityImageBinding activityImageBinding;
    ArrayList<MediaFile> arrayList;
    RecoveredImageAdapter recoveredImageAdapter;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityImageBinding = ActivityImageBinding.inflate(getLayoutInflater());
        setContentView(activityImageBinding.getRoot());
        activityImageBinding.toolbar.setTitle("Images");
        activityImageBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityImageBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityImageBinding.toolbar);

        activityImageBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        arrayList = new ArrayList<>();
        activityImageBinding.recyclerViewRecoveredImage.setLayoutManager(new GridLayoutManager(ImageActivity.this, 3));

        loadPictureStatuses();


    }

    private void loadPictureStatuses() {
        if (Consts.REC_IMG_DIR.exists())
        {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    File[] mediaFiles = Consts.REC_IMG_DIR.listFiles();

                    if (mediaFiles != null && mediaFiles.length > 0) {
                        Arrays.sort(mediaFiles);

                        for (final File mediaFile : mediaFiles) {
                            MediaFile media = new MediaFile(mediaFile, mediaFile.getName(),
                                    mediaFile.getAbsolutePath());

                            media.setThumbnail(getThumbnail(media));

                            if (!media.isVideo())
                            {
                                arrayList.add(media);
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                activityImageBinding.imageProgressBar.setVisibility(View.GONE);
                                recoveredImageAdapter = new RecoveredImageAdapter(arrayList, getApplicationContext());
                                activityImageBinding.recyclerViewRecoveredImage.setAdapter(recoveredImageAdapter);
                                recoveredImageAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                    else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                activityImageBinding.imageProgressBar.setVisibility(View.GONE);
                                Toast.makeText(ImageActivity.this, "No file found", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }).start();
        }else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    activityImageBinding.imageProgressBar.setVisibility(View.GONE);
                    Toast.makeText(ImageActivity.this, "No such folder exists", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void navigateToNextActivity(MediaFile status)
    {
        Intent intent = new Intent(ImageActivity.this, RecoveredImageViewActivity.class);
        intent.putExtra("image_path", status.getPath());
        intent.putExtra("title", status.getName());
        startActivity(intent);
    }
    public Bitmap getThumbnail(MediaFile mediaFile)
    {
        if (mediaFile.isVideo())
        {
            return ThumbnailUtils.createVideoThumbnail(mediaFile.getFile().getAbsolutePath(),
                    MediaStore.Video.Thumbnails.MICRO_KIND);
        }
        else {
            return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mediaFile.getFile().getAbsolutePath())
                    ,Consts.THUMBSIZE
                    ,Consts.THUMBSIZE);
        }
    }
}