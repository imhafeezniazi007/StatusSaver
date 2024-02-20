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

import com.example.statussaver.Adapters.RecoveredVideoAdapter;
import com.example.statussaver.Adapters.SavedVideoAdapter;
import com.example.statussaver.Fragments.SavedVideoFragment;
import com.example.statussaver.Models.MediaFile;
import com.example.statussaver.R;
import com.example.statussaver.Utils.Consts;
import com.example.statussaver.databinding.ActivityVideoBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class VideoActivity extends AppCompatActivity {

    ActivityVideoBinding activityVideoBinding;
    Handler handler = new Handler();
    ArrayList<MediaFile> videoArrayList;
    RecoveredVideoAdapter recoveredVideoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVideoBinding = ActivityVideoBinding.inflate(getLayoutInflater());
        setContentView(activityVideoBinding.getRoot());
        activityVideoBinding.toolbar.setTitle("Videos");
        activityVideoBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityVideoBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityVideoBinding.toolbar);

        activityVideoBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VideoActivity.this, WhatsDeleteActivity.class));
                finish();
            }
        });

        videoArrayList = new ArrayList<>();
        activityVideoBinding.recyclerViewVideo.setHasFixedSize(true);
        activityVideoBinding.recyclerViewVideo.setLayoutManager(new GridLayoutManager(this, 3));
        loadVideoStatuses();
    }

    private void loadVideoStatuses() {
        if (Consts.REC_VID_DIR.exists())
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File[] mediaFiles = Consts.REC_VID_DIR.listFiles();

                    if (mediaFiles != null && mediaFiles.length > 0) {
                        Arrays.sort(mediaFiles);

                        for (final File mediaFile : mediaFiles) {
                            MediaFile media = new MediaFile(mediaFile, mediaFile.getName(),
                                    mediaFile.getAbsolutePath());

                            media.setThumbnail(getThumbnail(media));

                            if (media.isVideo())
                            {
                                videoArrayList.add(media);
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                activityVideoBinding.videoProgressBar.setVisibility(View.GONE);
                                recoveredVideoAdapter = new RecoveredVideoAdapter(videoArrayList, getApplicationContext());
                                activityVideoBinding.recyclerViewVideo.setAdapter(recoveredVideoAdapter);
                                recoveredVideoAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                    else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                activityVideoBinding.videoProgressBar.setVisibility(View.GONE);
                                Toast.makeText(VideoActivity.this, "Does not exist", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }
    }

    public void navigateToNextActivity(MediaFile status)
    {
        Intent intent = new Intent(VideoActivity.this, RecoveredVideoViewActivity.class);
        intent.putExtra("image_path", status.getPath());
        intent.putExtra("title", status.getName());
        startActivity(intent);
    }
    private Bitmap getThumbnail(MediaFile status)
    {
        if (status.isVideo())
        {
            return ThumbnailUtils.createVideoThumbnail(status.getFile().getAbsolutePath(),
                    MediaStore.Video.Thumbnails.MICRO_KIND);
        }
        else {
            return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(status.getFile().getAbsolutePath())
                    ,Consts.THUMBSIZE
                    ,Consts.THUMBSIZE);
        }
    }

}