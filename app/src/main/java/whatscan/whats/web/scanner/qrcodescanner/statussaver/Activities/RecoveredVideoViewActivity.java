package whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ActivityRecoveredVideoViewBinding;

public class RecoveredVideoViewActivity extends AppCompatActivity {

    ActivityRecoveredVideoViewBinding activityRecoveredVideoViewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRecoveredVideoViewBinding = ActivityRecoveredVideoViewBinding.inflate(getLayoutInflater());
        setContentView(activityRecoveredVideoViewBinding.getRoot());
        Intent intent = getIntent();
        String videoPath = intent.getStringExtra("image_path");
        String title = intent.getStringExtra("title");

        activityRecoveredVideoViewBinding.toolbar.setTitle(title);
        activityRecoveredVideoViewBinding.toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(activityRecoveredVideoViewBinding.toolbar);



        File videoFile = new File(videoPath);

        Uri uri = Uri.fromFile(videoFile);

        activityRecoveredVideoViewBinding.savedStatusVideo.setVideoURI(uri);
        MediaController mediaController = new MediaController(RecoveredVideoViewActivity.this);
        mediaController.setAnchorView(activityRecoveredVideoViewBinding.savedStatusVideo);
        activityRecoveredVideoViewBinding.savedStatusVideo.setMediaController(mediaController);
        activityRecoveredVideoViewBinding.savedStatusVideo.start();

        activityRecoveredVideoViewBinding.shareIconVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("video/mp4");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(sharingIntent, "Share video using"));
            }
        });

        activityRecoveredVideoViewBinding.deleteIconVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(RecoveredVideoViewActivity.this);
                builder.setMessage("Are you sure you want to delete this video?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                File file = new File(videoPath);
                                if (file.exists()) {
                                    boolean deleted = file.delete();
                                    if (deleted) {
                                        Toast.makeText(RecoveredVideoViewActivity.this, "Video deleted successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RecoveredVideoViewActivity.this, SavedStatusActivity.class));
                                        finishAffinity();
                                    } else {
                                        Toast.makeText(RecoveredVideoViewActivity.this, "Please try again later.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


            }
        });
    }
    }