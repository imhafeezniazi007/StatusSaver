package com.example.statussaver.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.statussaver.Activities.ChatsViewActivity;
import com.example.statussaver.R;
import com.example.statussaver.Models.NotificationText;
import com.example.statussaver.databinding.ItemRecoveredChatsBinding;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {
    private List<NotificationText> notificationTextList;
    private Context context;

    public ChatsAdapter(Context context, List<NotificationText> notificationTextList) {
        this.context = context;
        this.notificationTextList = notificationTextList;
    }

    @NonNull
    @Override
    public ChatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recovered_chats, parent, false);
        return new ChatsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsAdapter.ViewHolder holder, int position) {
        NotificationText notificationText = notificationTextList.get(position);
        if (notificationText!=null) {
            holder.binding.chatHeading.setText(notificationText.getText());
            Log.e("_issue", "onBindViewHolder: No text in db" );
        }
        //holder.binding.chatHeading.setText(scan.getContent());

        holder.binding.relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                v.getContext().startActivity(new Intent(context, ChatsViewActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationTextList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemRecoveredChatsBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemRecoveredChatsBinding.bind(itemView);
        }
    }
}