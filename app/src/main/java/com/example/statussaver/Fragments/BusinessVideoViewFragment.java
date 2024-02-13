package com.example.statussaver.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.example.statussaver.Adapters.BusinessVideoAdapter;
import com.example.statussaver.Models.Status;
import com.example.statussaver.R;
import com.example.statussaver.databinding.FragmentBusinessVideoViewBinding;
import com.example.statussaver.databinding.FragmentVideoStatusViewBinding;

import java.io.File;
import java.io.IOException;

public class BusinessVideoViewFragment extends Fragment {

    FragmentBusinessVideoViewBinding fragmentBusinessVideoViewBinding;
    BusinessVideoFragment videoAdapter;

    public BusinessVideoViewFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_video_view, container, false);
        fragmentBusinessVideoViewBinding = FragmentBusinessVideoViewBinding.bind(view);

        if (getArguments() != null) {
            String path = getArguments().getString("path");


            File videoFile = new File(path);
            // Parse the URI string into a Uri object
            Uri uri = Uri.fromFile(videoFile);

            fragmentBusinessVideoViewBinding.videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(requireActivity());
            mediaController.setAnchorView(fragmentBusinessVideoViewBinding.videoView);
            fragmentBusinessVideoViewBinding.videoView.setMediaController(mediaController);
        }


        fragmentBusinessVideoViewBinding.logo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Bundle bundle = getArguments();
                    if (bundle != null) {
                        Status status = (Status) bundle.getSerializable("key");
                    if (videoAdapter != null)
                    {
                        try {
                            videoAdapter.downloadVideo(status);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        Log.e("BusinessVideoViewFragment", "VideoAdapter is null");
                    }
                }
            }
        });

        return view;
    }
}