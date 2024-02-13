package com.example.statussaver.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.statussaver.Models.MediaFile;
import com.example.statussaver.Models.Status;
import com.example.statussaver.R;
import com.example.statussaver.databinding.ItemQrscanBinding;
import com.example.statussaver.databinding.ItemSavedStatusBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class SavedStatusAdapter extends RecyclerView.Adapter<SavedStatusAdapter.ViewHolder> {
    private List<MediaFile> mediaFiles;

    public SavedStatusAdapter(List<MediaFile> mediafiles) {
        this.mediaFiles = mediafiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@android.support.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_status, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MediaFile file = mediaFiles.get(position);
        holder.itemSavedStatusBinding.imageThumbnail.setImageBitmap(file.getThumbnail());
    }


    @Override
    public int getItemCount() {
        return mediaFiles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemSavedStatusBinding itemSavedStatusBinding;

        public ViewHolder(@android.support.annotation.NonNull View itemView) {
            super(itemView);
            itemSavedStatusBinding = ItemSavedStatusBinding.bind(itemView);
        }
    }

}
