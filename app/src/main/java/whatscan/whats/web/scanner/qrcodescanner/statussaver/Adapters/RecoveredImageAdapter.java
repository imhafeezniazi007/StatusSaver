package whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities.ImageActivity;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.MediaFile;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ItemSavedStatusBinding;

public class RecoveredImageAdapter extends RecyclerView.Adapter<RecoveredImageAdapter.ViewHolder> {
    private List<MediaFile> mediaFiles;
    private final Context context;


    public RecoveredImageAdapter(List<MediaFile> mediafiles, Context context) {
        this.mediaFiles = mediafiles;
        this.context = context;
    }

    @NonNull
    @Override
    public RecoveredImageAdapter.ViewHolder onCreateViewHolder(@android.support.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saved_status, parent, false);
        return new RecoveredImageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MediaFile status = mediaFiles.get(position);
        holder.itemSavedStatusBinding.imageThumbnail.setImageBitmap(status.getThumbnail());

        holder.itemSavedStatusBinding.imageThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MediaFile status = mediaFiles.get(position);
                    if (status != null && status.getThumbnail() != null) {
                        ((ImageActivity) context).navigateToNextActivity(status);
                    } else {
                        Log.e("ImageAdapter", "Invalid status or thumbnail is null");
                    }
                } else {
                    Log.e("ImageAdapter", "Invalid position");
                }
            }
        });
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
