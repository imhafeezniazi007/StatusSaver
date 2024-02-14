package com.example.statussaver.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statussaver.Activities.BusinessStatusActivity;
import com.example.statussaver.Activities.MainActivity;
import com.example.statussaver.Fragments.BusinessVideoFragment;
import com.example.statussaver.Fragments.VideoFragment;
import com.example.statussaver.Models.Status;
import com.example.statussaver.R;
import com.example.statussaver.databinding.ItemStatusBinding;

import java.util.List;

public class BusinessVideoAdapter extends RecyclerView.Adapter<BusinessVideoAdapter.VideoViewHolder> {


    private final List<Status> statusList;
    Context context;
    BusinessVideoFragment businessVideoFragment;

    public BusinessVideoAdapter(List<Status> statusList, Context context, BusinessVideoFragment businessVideoFragment) {
        this.statusList = statusList;
        this.context = context;
        this.businessVideoFragment = businessVideoFragment;
    }
    @NonNull
    @Override
    public BusinessVideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status,parent,false);
        return new BusinessVideoAdapter.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessVideoAdapter.VideoViewHolder holder, int position) {
        Status status = statusList.get(position);
        holder.binding.imageThumbnail.setImageBitmap(status.getThumbnail());

        holder.binding.imageThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = holder.getAbsoluteAdapterPosition();

                if (context instanceof BusinessStatusActivity) {
                    if (status.getThumbnail() != null) {

                        if (status.isVideo()) {


                            ((BusinessStatusActivity) context).navigateToForthFragment(status);

                        }else {
                            Log.e("FirstFragment", "Not a video");
                        }
                    } else {
                        Log.e("FirstFragment", "Thumbnail not found");
                    }
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
