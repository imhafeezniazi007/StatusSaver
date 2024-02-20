package com.example.statussaver.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statussaver.Activities.ImageActivity;
import com.example.statussaver.Activities.SavedStatusActivity;
import com.example.statussaver.Activities.VideoActivity;
import com.example.statussaver.Fragments.SavedVideoFragment;
import com.example.statussaver.Models.MediaFile;
import com.example.statussaver.R;
import com.example.statussaver.databinding.ItemStatusBinding;

import java.util.List;

public class RecoveredVideoAdapter extends RecyclerView.Adapter<RecoveredVideoAdapter.VideoViewHolder> {


    private final List<MediaFile> statusList;
    private final Context context;

    public RecoveredVideoAdapter(List<MediaFile> statusList, Context context) {
        this.statusList = statusList;
        this.context = context;
    }
    @NonNull
    @Override
    public RecoveredVideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status,parent,false);
        return new RecoveredVideoAdapter.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        MediaFile status = statusList.get(position);
        holder.binding.imageThumbnail.setImageBitmap(status.getThumbnail());

        holder.binding.imageThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (status.getThumbnail() != null) {

                    if (status.isVideo()) {

                        ((VideoActivity) context).navigateToNextActivity(status);

                    }else {
                        Log.e("FirstFragment", "Not a video");
                    }
                } else {
                    Log.e("FirstFragment", "Thumbnail not found");
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder
    {
        ItemStatusBinding binding;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStatusBinding.bind(itemView);
        }

    }
}
