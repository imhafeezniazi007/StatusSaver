package com.example.statussaver.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ProgressImageDialogBinding;

public class ProgressImageDialog extends Dialog {

    private Drawable image;
    ProgressImageDialogBinding progressImageDialogBinding;

    public ProgressImageDialog(Context context) {
        super(context);

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.progress_image_dialog, null);
        setContentView(view);
    }
}