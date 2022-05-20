package com.example.finalapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.finalapp.R;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channel;

public class MovieDownloadService extends Service {
    private MutableLiveData<Integer> progress;

    public MutableLiveData<Integer> getProgress() {
        return progress;
    }

    public void setProgress(MutableLiveData<Integer> progress) {
        this.progress = progress;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        progress = new MutableLiveData<>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder{
        public MovieDownloadService getService(){
            return MovieDownloadService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // notification
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channelId0", "channelName0", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("this is my channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channelId0");
        builder.setSmallIcon(R.drawable.camera_icon);
        builder.setContentTitle("this is my service");
        builder.setContentText("this is my service");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification notification = builder.build();
        startForeground(1, notification);

        //
        new Thread(() -> {
            try {
                URL url = new URL(intent.getStringExtra("movieUrl"));
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();

                int total = urlConnection.getContentLength();
                Log.e("TAG", "lengthOfFile: " + total);
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
                OutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/vinh.mp4");
                byte[] data = new byte[1024];
                int currentTotal = 0;
                int count = 0;
                while (true){
                    // read data
                    count = inputStream.read(data);
                    // write data
                    if(count != -1){
                        outputStream.write(data, 0, count);
                        currentTotal += count;
                        int percent = (int)((currentTotal * 1.0 / total) * 100);
                        progress.postValue(percent);
                    }
                    else{
                        break;
                    }
                }

                // close
                inputStream.close();
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        }).start();

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy: ");
    }
}
