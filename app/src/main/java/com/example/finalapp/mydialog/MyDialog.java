package com.example.finalapp.mydialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalapp.R;

public class MyDialog {
    private Dialog dialog;
    private Context context;
    private int imageSrc;
    private String title;
    private String content;
    private OnAgree onAgree;
    private OnCancer onCancer;

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(int imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public OnAgree getOnAgree() {
        return onAgree;
    }

    public void setOnAgree(OnAgree onAgree) {
        this.onAgree = onAgree;
    }

    public OnCancer getOnCancer() {
        return onCancer;
    }

    public void setOnCancer(OnCancer onCancer) {
        this.onCancer = onCancer;
    }

    public MyDialog(Context context){
        this.context = context;
        dialog = new Dialog(this.context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.message_dialog);
        title = "Thông báo";
        content = "Thông báo";
        imageSrc = R.drawable.cloud_service;
        onAgree = () -> { };
        onCancer = () -> { };
    }
    public void show(){
        ImageView imageView = dialog.findViewById(R.id.imageView);
        TextView textViewTitle = dialog.findViewById(R.id.textViewTitle);
        TextView textViewContent = dialog.findViewById(R.id.textViewContent);
        Button btnAgree = dialog.findViewById(R.id.btnAgree);
        Button btnCancer = dialog.findViewById(R.id.btnCancer);
        Glide.with(context).load(imageSrc).into(imageView);
        textViewTitle.setText(title);
        textViewContent.setText(content);
        btnAgree.setOnClickListener((View view) -> {
            onAgree.agree();
            dialog.dismiss();
        });
        btnCancer.setOnClickListener((View view) -> {
            onCancer.cancer();
            dialog.dismiss();
        });

        dialog.show();
    }
    public interface OnAgree{
        void agree();
    }
    public interface OnCancer{
        void cancer();
    }
}
