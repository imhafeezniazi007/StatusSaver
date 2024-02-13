package com.example.statussaver.Fragments;

import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

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


import com.example.statussaver.R;
import com.example.statussaver.databinding.FragmentVideoBinding;
import com.example.statussaver.databinding.FragmentVideoStatusViewBinding;

import java.io.File;


public class VideoStatusViewFragment extends Fragment {

    FragmentVideoStatusViewBinding fragmentVideoViewBinding;

    public VideoStatusViewFragment() {
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
                // Parse the URI string into a Uri object
            Uri uri = Uri.fromFile(videoFile);

            fragmentVideoViewBinding.videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(requireActivity());
            mediaController.setAnchorView(fragmentVideoViewBinding.videoView);
            fragmentVideoViewBinding.videoView.setMediaController(mediaController);
        }

        return view;
    }
}