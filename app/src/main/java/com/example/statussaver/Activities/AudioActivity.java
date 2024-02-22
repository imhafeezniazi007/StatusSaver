package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.statussaver.Adapters.RecoveredAudioAdapter;
import com.example.statussaver.Adapters.RecoveredImageAdapter;
import com.example.statussaver.Models.MediaFile;
import com.example.statussaver.R;
import com.example.statussaver.Utils.Consts;
import com.example.statussaver.databinding.ActivityAudioBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class AudioActivity extends AppCompatActivity {

    ActivityAudioBinding activityAudioBinding;
    ArrayList<MediaFile> arrayList;
    RecoveredAudioAdapter recoveredAudioAdapter;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAudioBinding = ActivityAudioBinding.inflate(getLayoutInflater());
        setContentView(activityAudioBinding.getRoot());
        activityAudioBinding.toolbar.setTitle("Audios");
        activityAudioBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityAudioBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityAudioBinding.toolbar);

        activityAudioBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        arrayList = new ArrayList<>();
        activityAudioBinding.recyclerViewRecovered.setLayoutManager(new GridLayoutManager(AudioActivity.this, 3));

        loadAudios();


    }

    private void loadAudios() {
        if (Consts.REC_AUD_DIR.exists()) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    File[] mediaFiles = Consts.REC_AUD_DIR.listFiles();

                    if (mediaFiles != null && mediaFiles.length > 0) {
                        Arrays.sort(mediaFiles);

                        for (final File mediaFile : mediaFiles) {
                            MediaFile media = new MediaFile(mediaFile, mediaFile.getName(),
                                    mediaFile.getAbsolutePath());

                            arrayList.add(media);
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                activityAudioBinding.progressBar.setVisibility(View.GONE);
                                recoveredAudioAdapter = new RecoveredAudioAdapter(arrayList, getApplicationContext());
                                activityAudioBinding.recyclerViewRecovered.setAdapter(recoveredAudioAdapter);
                                recoveredAudioAdapter.notifyDataSetChanged();
                            }
                        });

                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                activityAudioBinding.progressBar.setVisibility(View.GONE);
                                Toast.makeText(AudioActivity.this, "No file found", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }).start();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    activityAudioBinding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(AudioActivity.this, "No such folder exists", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void navigateToIntent(MediaFile status)
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("audio/*");
        Uri uri = Uri.fromFile(status.getFile());
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

}
