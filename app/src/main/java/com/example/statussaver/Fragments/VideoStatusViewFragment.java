package com.example.statussaver.Fragments;

import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;


import com.example.statussaver.Models.Status;
import com.example.statussaver.R;
import com.example.statussaver.Utils.Consts;
import com.example.statussaver.databinding.FragmentVideoBinding;
import com.example.statussaver.databinding.FragmentVideoStatusViewBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;


public class VideoStatusViewFragment extends Fragment {

    FragmentVideoStatusViewBinding fragmentVideoViewBinding;
    Status vStatus;
    List<Status> statusList;

    public VideoStatusViewFragment(Status status) {
        this.vStatus = status;
    }

    public VideoStatusViewFragment(List<Status> statusList) {
        this.statusList = statusList;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_status_view, container, false);
        fragmentVideoViewBinding = FragmentVideoStatusViewBinding.bind(view);

        if (getArguments() != null) {
            String path = getArguments().getString("path");


            File videoFile = new File(path);

            Uri uri = Uri.fromFile(videoFile);

            fragmentVideoViewBinding.videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(requireActivity());
            mediaController.setAnchorView(fragmentVideoViewBinding.videoView);
            fragmentVideoViewBinding.videoView.setMediaController(mediaController);
            fragmentVideoViewBinding.videoView.start();
        }


        fragmentVideoViewBinding.logo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments() != null) {
                    try {
                        downloadVideo(vStatus);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        fragmentVideoViewBinding.logo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse(vStatus.getPath());

                sharingIntent.setType("video/mp4");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share video using"));

            }
        });

        return view;
    }

    public void downloadVideo(Status status) throws IOException {
        File file = new File(Consts.APP_DIR);
        if (!file.exists())
        {
            file.mkdirs();
        }
        File destFile = new File(file+File.separator + status.getTitle());
        if (destFile.exists())
        {
            destFile.delete();
        }

        copyFile(status.getFile(),destFile);

        Toast.makeText(getContext(), "Download complete...", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(destFile));
        getActivity().sendBroadcast(intent);
    }

    private void copyFile(File file, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
        {
            destFile.getParentFile().mkdirs();
        }
        if (!destFile.exists())
        {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        source = new FileInputStream(file).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        destination.transferFrom(source,0,source.size());

        source.close();
        destination.close();
    }
}