package whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities.DocumentActivity;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.MediaFile;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ItemRecoveredFilesBinding;

public class RecoveredDocumentAdapter extends RecyclerView.Adapter<RecoveredDocumentAdapter.ViewHolder> {
    private List<MediaFile> mediaFiles;
    private final Context context;


    public RecoveredDocumentAdapter(List<MediaFile> mediafiles, Context context) {
        this.mediaFiles = mediafiles;
        this.context = context;
    }

    @NonNull
    @Override
    public RecoveredDocumentAdapter.ViewHolder onCreateViewHolder(@android.support.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recovered_files, parent, false);
        return new RecoveredDocumentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MediaFile status = mediaFiles.get(position);
        Drawable drawable = holder.itemView.getResources().getDrawable(R.drawable.document);
        holder.itemRecoveredFilesBinding.imageThumbnail.setImageDrawable(drawable);
        holder.itemRecoveredFilesBinding.imageTitle.setText(status.getName());

        holder.itemRecoveredFilesBinding.imageThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MediaFile status = mediaFiles.get(position);
                    if (status != null) {
                        ((DocumentActivity) context).navigateToIntent(status);
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
        ItemRecoveredFilesBinding itemRecoveredFilesBinding;

        public ViewHolder(@android.support.annotation.NonNull View itemView) {
            super(itemView);
            itemRecoveredFilesBinding = ItemRecoveredFilesBinding.bind(itemView);
        }
    }

}
