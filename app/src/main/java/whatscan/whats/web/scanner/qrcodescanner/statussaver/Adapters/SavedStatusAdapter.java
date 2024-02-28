package whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.MediaFile;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ItemSavedStatusBinding;

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
