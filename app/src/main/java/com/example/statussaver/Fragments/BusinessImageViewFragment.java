package com.example.statussaver.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statussaver.Adapters.BusinessImageAdapter;
import com.example.statussaver.Adapters.ImageAdapter;
import com.example.statussaver.R;
import com.example.statussaver.databinding.FragmentBusinessImageViewBinding;
import com.example.statussaver.databinding.FragmentImageStatusViewBinding;

public class BusinessImageViewFragment extends Fragment {

    FragmentBusinessImageViewBinding fragmentBusinessImageViewBinding;
    BusinessImageAdapter imageAdapter;

    public BusinessImageViewFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_image_view, container, false);
        fragmentBusinessImageViewBinding = FragmentBusinessImageViewBinding.bind(view);
        if (getArguments() != null) {
            String imagePath = getArguments().getString("image_path");
            if (imagePath != null) {

                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

                if (bitmap != null) {
                    fragmentBusinessImageViewBinding.imageView2.setImageBitmap(bitmap);
                } else {
                    Log.e("ThirdFragment", imagePath);
                }
            }
        } else {
            Log.e("ThirdFragment", "Image path is null.");
        }

        fragmentBusinessImageViewBinding.logo1.setOnClickListener(new View.OnClickListener() {
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