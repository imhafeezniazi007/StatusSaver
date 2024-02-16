package com.example.statussaver.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statussaver.Models.NotificationText;
import com.example.statussaver.databinding.ItemChatViewBinding;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private List<NotificationText> messages;
    private Context context;

    public MessagesAdapter(Context context, List<NotificationText> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemChatViewBinding itemBinding = ItemChatViewBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationText message = messages.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemChatViewBinding binding;

        public ViewHolder(ItemChatViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NotificationText message) {
            binding.textMessageChat.setText(message.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm", Locale.getDefault());
            String formattedDate = sdf.format(message.getTimestamp());
            binding.textTimestamp.setText(formattedDate);
        }
    }

    public void setData(List<NotificationText> newData) {
        messages.clear();
        messages.addAll(newData);
        notifyDataSetChanged();
    }
}
