package com.example.statussaver.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;

import com.example.statussaver.Adapters.BusinessVideoAdapter;
import com.example.statussaver.Models.Status;
import com.example.statussaver.R;
import com.example.statussaver.Utils.Consts;
import com.example.statussaver.databinding.FragmentBusinessVideoViewBinding;
import com.example.statussaver.databinding.FragmentVideoStatusViewBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

public class BusinessVideoViewFragment extends Fragment {

    FragmentBusinessVideoViewBinding fragmentBusinessVideoViewBinding;
    BusinessVideoFragment videoAdapter;
    Status bVideoStatus;
    List<Status> statusList;


    public BusinessVideoViewFragment(List<Status> statusList) {
        this.statusList = statusList;
    }

    public BusinessVideoViewFragment(Status status) {
        this.bVideoStatus = status;
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
            Uri uri = Uri.fromFile(videoFile);

            fragmentBusinessVideoViewBinding.videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(requireActivity());
            mediaController.setAnchorView(fragmentBusinessVideoViewBinding.videoView);
            fragmentBusinessVideoViewBinding.videoView.setMediaController(mediaController);
            fragmentBusinessVideoViewBinding.videoView.start();
        }


        fragmentBusinessVideoViewBinding.logo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Bundle bundle = getArguments();
                    if (bundle != null) {
                        try {
                            downloadVideo(bVideoStatus);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                }
            }
        });

        return view;
    }

    public void downloadVideo(Status status) throws IOException {
        File file = new File(Consts.APP_DIR_BUSINESS);
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