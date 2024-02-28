package whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters.RecoveredDocumentAdapter;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.MediaFile;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.Consts;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ActivityDocumentBinding;

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
                                recoveredDocumentAdapter = new RecoveredDocumentAdapter(arrayList, DocumentActivity.this);
                                activityDocumentBinding.recyclerViewRecovered.setAdapter(recoveredDocumentAdapter);
                                recoveredDocumentAdapter.notifyDataSetChanged();
                            }
                        });

                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                activityDocumentBinding.progressBar.setVisibility(View.GONE);
                                activityDocumentBinding.textNoItem.setVisibility(View.VISIBLE);
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
                    activityDocumentBinding.textNoItem.setVisibility(View.VISIBLE);
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
