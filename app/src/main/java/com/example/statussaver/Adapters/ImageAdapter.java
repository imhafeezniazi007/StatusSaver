package com.example.statussaver.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statussaver.Activities.MainActivity;
import com.example.statussaver.Fragments.ImageFragment;
import com.example.statussaver.Models.Status;
import com.example.statussaver.R;
import com.example.statussaver.databinding.ItemStatusBinding;

import java.io.File;
import java.io.IOException;
import java.util.List;
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final List<Status> statusList;
    private final Context context;
    private final ImageFragment imageFragment;

    public ImageAdapter(List<Status> statusList, Context context, ImageFragment imageFragment) {
        this.statusList = statusList;
        this.context = context;
        this.imageFragment = imageFragment;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Status status = statusList.get(position);
        holder.binding.imageThumbnail.setImageBitmap(status.getThumbnail());

        holder.binding.imageThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Status status = statusList.get(position);
                    if (status != null && status.getThumbnail() != null) {
                        String path = status.getPath();
                        ((MainActivity) context).navigateToThirdFragment(path, position);
                    } else {
                        Log.e("ImageAdapter", "Invalid status or thumbnail is null");
                    }
                } else {
                    Log.e("ImageAdapter", "Invalid position");
                }
            }
        });
    }

    public void downloadImage(int position) {
        Status status = statusList.get(position);
        if (status != null) {
            try {
                imageFragment.downloadImage(status);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ItemStatusBinding binding;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStatusBinding.bind(itemView);
        }
    }
}
