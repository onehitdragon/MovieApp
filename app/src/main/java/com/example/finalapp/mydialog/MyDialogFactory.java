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

    public static MyDialog createAddedLaterMovie(Context context){
        MyDialog myDialog = new MyDialog(context);
        myDialog.setImageSrc(R.drawable.clock);
        myDialog.setTitle("Thành công");
        myDialog.setContent("Đã thêm vào danh sách xem sau");

        return myDialog;
    }

    public static MyDialog createAddedDownload(Context context){
        MyDialog myDialog = new MyDialog(context);
        myDialog.setImageSrc(R.drawable.download);
        myDialog.setTitle("Thành công");
        myDialog.setContent("Đã thêm vào danh sách tải xuống");

        return myDialog;
    }

    public static MyDialog createAddedDownloadFail(Context context){
        MyDialog myDialog = new MyDialog(context);
        myDialog.setImageSrc(R.drawable.download_fail);
        myDialog.setTitle("Thất bại");
        myDialog.setContent("Tập này đã được tải xuống");

        return myDialog;
    }
}
