package com.example.statussaver.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.statussaver.Activities.ChatsViewActivity;
import com.example.statussaver.R;
import com.example.statussaver.databinding.ItemRecoveredChatsBinding;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {
    private List<Pair<String, String>> senderAndLatestTextList;
    private Context context;

    public ChatsAdapter(Context context, List<Pair<String, String>> senderAndLatestTextList) {
        this.context = context;
        this.senderAndLatestTextList = senderAndLatestTextList;
    }

    @NonNull
    @Override
    public ChatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recovered_chats, parent, false);
        return new ChatsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsAdapter.ViewHolder holder, int position) {
        Pair<String, String> senderAndLatestText = senderAndLatestTextList.get(position);
        holder.binding.chatHeading.setText(senderAndLatestText.first);
        holder.binding.messageDescription.setText(senderAndLatestText.second);

        holder.binding.relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, ChatsViewActivity.class);
                intent.putExtra("sender_name", senderAndLatestText.first);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return senderAndLatestTextList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemRecoveredChatsBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemRecoveredChatsBinding.bind(itemView);
        }
    }

    public void setData(List<Pair<String, String>> newData) {
        senderAndLatestTextList.clear();
        senderAndLatestTextList.addAll(newData);
        notifyDataSetChanged();
    }
}
