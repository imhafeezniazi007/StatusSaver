package whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ActivitySavedImageViewBinding;

public class SavedImageViewActivity extends AppCompatActivity {

    ActivitySavedImageViewBinding activitySavedImageViewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySavedImageViewBinding = ActivitySavedImageViewBinding.inflate(getLayoutInflater());
        setContentView(activitySavedImageViewBinding.getRoot());
        activitySavedImageViewBinding.toolbar.setNavigationIcon(R.drawable.back);

        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("image_path");
        String title = intent.getStringExtra("title");

        activitySavedImageViewBinding.toolbar.setTitle(title);

        setSupportActionBar(activitySavedImageViewBinding.toolbar);

        activitySavedImageViewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



            if (imagePath != null) {

                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

                if (bitmap != null) {
                    activitySavedImageViewBinding.savedStatusImg.setImageBitmap(bitmap);
                } else {
                    Log.e("ThirdFragment", imagePath);
                }
            }

            activitySavedImageViewBinding.shareIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    Uri screenshotUri = Uri.parse(imagePath);

                    sharingIntent.setType("image/jpeg");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                    startActivity(Intent.createChooser(sharingIntent, "Share image using"));

                }
            });

            activitySavedImageViewBinding.deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(SavedImageViewActivity.this);
                    builder.setMessage("Are you sure you want to delete this image?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    File file = new File(imagePath);
                                    if (file.exists()) {
                                        boolean deleted = file.delete();
                                        if (deleted) {
                                            Toast.makeText(SavedImageViewActivity.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SavedImageViewActivity.this, SavedStatusActivity.class));
                                            finishAffinity();
                                        } else {
                                            Toast.makeText(SavedImageViewActivity.this, "Please try again later.", Toast.LENGTH_SHORT).show();
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