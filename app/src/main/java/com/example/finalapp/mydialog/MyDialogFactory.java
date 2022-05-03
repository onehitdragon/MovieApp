package com.example.finalapp.mydialog;

import android.content.Context;

import com.example.finalapp.R;

public class MyDialogFactory {
    public static MyDialog createErrorServerDialog(Context context){
        MyDialog myDialog = new MyDialog(context);
        myDialog.setImageSrc(R.drawable.cloud_service);
        myDialog.setTitle("Lỗi");
        myDialog.setContent("Máy chủ không phản hồi");
        return myDialog;
    }
}
