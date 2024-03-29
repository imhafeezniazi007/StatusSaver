package whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities.BusinessStatusActivity;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.BusinessImageFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.Status;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ItemStatusBinding;

public class BusinessImageAdapter extends RecyclerView.Adapter<BusinessImageAdapter.ImageViewHolder> {

private final List<Status> statusList;
private final Context context;
private final BusinessImageFragment businessImageFragment;

public BusinessImageAdapter(List<Status> statusList, Context context, BusinessImageFragment businessImageFragment) {
        this.statusList = statusList;
        this.context = context;
        this.businessImageFragment = businessImageFragment;
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
        ((BusinessStatusActivity) context).navigateToThirdFragment(status);
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
