package com.example.statussaver.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statussaver.Adapters.ImageAdapter;
import com.example.statussaver.Models.Status;
import com.example.statussaver.R;
import com.example.statussaver.Utils.Consts;
import com.example.statussaver.databinding.FragmentImageBinding;
import com.example.statussaver.databinding.FragmentImageStatusViewBinding;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageStatusViewFragment extends Fragment {

    FragmentImageStatusViewBinding fragmentImageStatusViewBinding;
    ImageAdapter imageAdapter;
    Context context;

    public ImageStatusViewFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_status_view, container, false);
        fragmentImageStatusViewBinding = FragmentImageStatusViewBinding.bind(view);
        if (getArguments() != null) {
            String imagePath = getArguments().getString("image_path");
            if (imagePath != null) {

                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

                if (bitmap != null) {
                    fragmentImageStatusViewBinding.imageView2.setImageBitmap(bitmap);
                } else {
                    Log.e("ThirdFragment", imagePath);
                }
            }
        } else {
            Log.e("ThirdFragment", "Image path is null.");
        }

        fragmentImageStatusViewBinding.logo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments() != null) {
                    int position = getArguments().getInt("position");
                    if (imageAdapter != null)
                    {
                        imageAdapter.downloadImage(position);
                    } else {
                        Log.e("ImageStatusViewFragment", "ImageAdapter is null");
                    }
                }
            }
        });
        return view;
    }
}