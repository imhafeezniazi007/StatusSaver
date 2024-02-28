package whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters.RecoveredImageAdapter;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.MediaFile;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.Consts;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ActivityImageBinding;

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
                                recoveredImageAdapter = new RecoveredImageAdapter(arrayList, ImageActivity.this);
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
                                View noItemView = getLayoutInflater().inflate(R.layout.layout_no_items, null);
                                activityImageBinding.recyclerViewRecoveredImage.addView(noItemView);
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
                    View noItemView = getLayoutInflater().inflate(R.layout.layout_no_items, null);
                    activityImageBinding.recyclerViewRecoveredImage.addView(noItemView);
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