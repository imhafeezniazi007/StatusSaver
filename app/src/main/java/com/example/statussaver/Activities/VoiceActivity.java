package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.statussaver.Adapters.RecoveredDocumentAdapter;
import com.example.statussaver.Adapters.RecoveredVoiceAdapter;
import com.example.statussaver.Models.MediaFile;
import com.example.statussaver.R;
import com.example.statussaver.Utils.Consts;
import com.example.statussaver.databinding.ActivityDocumentBinding;
import com.example.statussaver.databinding.ActivityVoiceBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class VoiceActivity extends AppCompatActivity {

    ActivityVoiceBinding activityVoiceBinding;
    ArrayList<MediaFile> arrayList;
    RecoveredVoiceAdapter recoveredVoiceAdapter;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVoiceBinding = ActivityVoiceBinding.inflate(getLayoutInflater());
        setContentView(activityVoiceBinding.getRoot());
        activityVoiceBinding.toolbar.setTitle("Voices");
        activityVoiceBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityVoiceBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityVoiceBinding.toolbar);

        activityVoiceBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        arrayList = new ArrayList<>();
        activityVoiceBinding.recyclerViewRecovered.setLayoutManager(new GridLayoutManager(VoiceActivity.this, 3));

        loadVocies();


    }

    private void loadVocies() {
        if (Consts.REC_VOICE_DIR.exists())
        {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    File[] mediaFiles = Consts.REC_VOICE_DIR.listFiles();

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
                                activityVoiceBinding.progressBar.setVisibility(View.GONE);
                                recoveredVoiceAdapter = new RecoveredVoiceAdapter(arrayList, getApplicationContext());
                                activityVoiceBinding.recyclerViewRecovered.setAdapter(recoveredVoiceAdapter);
                                recoveredVoiceAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                    else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                activityVoiceBinding.progressBar.setVisibility(View.GONE);
                                Toast.makeText(VoiceActivity.this, "No file found", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }).start();
        }else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    activityVoiceBinding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(VoiceActivity.this, "No such folder exists", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void navigateToIntent(MediaFile status)
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("audio/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, status.getFile().toURI());
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

}
