package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.statussaver.Adapters.RecoveredAudioAdapter;
import com.example.statussaver.Adapters.RecoveredDocumentAdapter;
import com.example.statussaver.Models.MediaFile;
import com.example.statussaver.R;
import com.example.statussaver.Utils.Consts;
import com.example.statussaver.databinding.ActivityDocumentBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class DocumentActivity extends AppCompatActivity {

    ActivityDocumentBinding activityDocumentBinding;
    ArrayList<MediaFile> arrayList;
    RecoveredDocumentAdapter recoveredDocumentAdapter;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDocumentBinding = ActivityDocumentBinding.inflate(getLayoutInflater());
        setContentView(activityDocumentBinding.getRoot());
        activityDocumentBinding.toolbar.setTitle("Document");
        activityDocumentBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityDocumentBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityDocumentBinding.toolbar);

        activityDocumentBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        arrayList = new ArrayList<>();
        activityDocumentBinding.recyclerViewRecovered.setLayoutManager(new GridLayoutManager(DocumentActivity.this, 3));

        loadDocuments();


    }

    private void loadDocuments() {
        if (Consts.REC_DOC_DIR.exists()) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    File[] mediaFiles = Consts.REC_DOC_DIR.listFiles();

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
                                activityDocumentBinding.progressBar.setVisibility(View.GONE);
                                recoveredDocumentAdapter = new RecoveredDocumentAdapter(arrayList, getApplicationContext());
                                activityDocumentBinding.recyclerViewRecovered.setAdapter(recoveredDocumentAdapter);
                                recoveredDocumentAdapter.notifyDataSetChanged();
                            }
                        });

                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                activityDocumentBinding.progressBar.setVisibility(View.GONE);
                                Toast.makeText(DocumentActivity.this, "No file found", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }).start();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    activityDocumentBinding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(DocumentActivity.this, "No such folder exists", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void navigateToIntent(MediaFile status)
    {
        Intent shareIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        shareIntent.setType("*/*");
        Uri uri = Uri.fromFile(status.getFile());
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(shareIntent, "Share via"));

    }

}
