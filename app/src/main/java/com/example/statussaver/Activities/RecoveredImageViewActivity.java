package com.example.statussaver.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivityRecoveredImageViewBinding;

import java.io.File;

public class RecoveredImageViewActivity extends AppCompatActivity {

    ActivityRecoveredImageViewBinding activityRecoveredImageViewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRecoveredImageViewBinding = ActivityRecoveredImageViewBinding.inflate(getLayoutInflater());
        setContentView(activityRecoveredImageViewBinding.getRoot());
        activityRecoveredImageViewBinding.toolbar.setNavigationIcon(R.drawable.back);



        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("image_path");
        String title = intent.getStringExtra("title");

        activityRecoveredImageViewBinding.toolbar.setTitle(title);

        setSupportActionBar(activityRecoveredImageViewBinding.toolbar);

        activityRecoveredImageViewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecoveredImageViewActivity.this, ImageActivity.class));
                finish();
            }
        });



        if (imagePath != null) {

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            if (bitmap != null) {
                activityRecoveredImageViewBinding.savedStatusImg.setImageBitmap(bitmap);
            } else {
                Log.e("ThirdFragment", imagePath);
            }
        }

        activityRecoveredImageViewBinding.shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse(imagePath);

                sharingIntent.setType("image/jpeg");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));

            }
        });

        activityRecoveredImageViewBinding.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(RecoveredImageViewActivity.this);
                builder.setMessage("Are you sure you want to delete this image?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                File file = new File(imagePath);
                                if (file.exists()) {
                                    boolean deleted = file.delete();
                                    if (deleted) {
                                        Toast.makeText(RecoveredImageViewActivity.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RecoveredImageViewActivity.this, SavedStatusActivity.class));
                                        finishAffinity();
                                    } else {
                                        Toast.makeText(RecoveredImageViewActivity.this, "Please try again later.", Toast.LENGTH_SHORT).show();
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