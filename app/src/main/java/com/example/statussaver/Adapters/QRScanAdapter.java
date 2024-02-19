package com.example.statussaver.Adapters;

import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statussaver.Activities.QRScannerActivity;
import com.example.statussaver.Models.QRScan;
import com.example.statussaver.R;
import com.example.statussaver.Utils.DBHelper;
import com.example.statussaver.databinding.ItemQrscanBinding;
import com.example.statussaver.databinding.ItemStatusBinding;

import java.util.List;

public class QRScanAdapter extends RecyclerView.Adapter<QRScanAdapter.ViewHolder> {
    private List<QRScan> scanList;
    private Context context;

    public QRScanAdapter(Context context, List<QRScan> scanList) {
        this.context = context;
        this.scanList = scanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qrscan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final QRScan scan = scanList.get(position);

        holder.binding.tvRecyclerData.setText(scan.getContent());

        holder.binding.tvRecyclerDataDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.itemView);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.popup_copy) {
                            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText(holder.binding.tvRecyclerData.getText().toString(),
                                    holder.binding.tvRecyclerData.getText().toString());
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show();
                            return true;
                        }

                        else if (item.getItemId() == R.id.popup_delete) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Are you sure you want to delete this item?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            DBHelper dbHelper = new DBHelper(context);
                                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                                            dbHelper.deleteAllQRCodes(db, (int) scan.getId());
                                            db.close();

                                            scanList.remove(scan);
                                            notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                            return true;
                        }

                        else if (item.getItemId() == R.id.popup_share) {
                            int position = holder.getAdapterPosition();

                            QRScan scan = scanList.get(position);
                            String shareContent = scan.getContent();
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
                            context.startActivity(Intent.createChooser(shareIntent, "Share via"));
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return scanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemQrscanBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemQrscanBinding.bind(itemView);
        }
    }
}

