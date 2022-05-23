package com.example.finalapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
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
import com.example.finalapp.model.Episode;
import com.example.finalapp.model.InfoDownloadMovie;
import com.example.finalapp.model.Movie;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class MovieDownloadService extends Service {
    private Queue<InfoDownloadMovie> listMovie;
    private boolean isStartDownload;

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
    public void onCreate() {
        super.onCreate();
        listMovie = new LinkedList<>();
        isStartDownload = false;
    }

    public void addToDownload(InfoDownloadMovie infoDownloadMovie){
        listMovie.add(infoDownloadMovie);
        updateNotification();
        if(!isStartDownload){
            startDownload();
            startForeground(1, createNotification());
            isStartDownload = true;
        }
    }

    public void removeFromDownload(InfoDownloadMovie _infoDownloadMovie){
        for (InfoDownloadMovie infoDownloadMovie : listMovie) {
            if(infoDownloadMovie.getMovie().getId() == _infoDownloadMovie.getMovie().getId() && infoDownloadMovie.getEpisode().getNumber() == _infoDownloadMovie.getEpisode().getNumber()){
                listMovie.remove(infoDownloadMovie);
                updateNotification();
                break;
            }
        }
    }

    private void startDownload(){
        new Thread(() -> {
            while (listMovie.size() != 0){
                InfoDownloadMovie infoDownloadMovie = listMovie.peek();
                if(infoDownloadMovie == null) break;

                try {
                    URL url = new URL(infoDownloadMovie.getEpisode().getSourceUrl());
                    URLConnection urlConnection = url.openConnection();
                    urlConnection.connect();

                    int total = urlConnection.getContentLength();
                    Log.e("TAG", "lengthOfFile: " + total);
                    InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
                    File destinationPath = infoDownloadMovie.getDestinationPath(this);

                    OutputStream outputStream = new FileOutputStream(destinationPath);
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
                            infoDownloadMovie.getProgress().postValue(percent);
                        }
                        else{
                            break;
                        }
                        // detect movie be remove
                        if(getInfoDownloadMovie(infoDownloadMovie) == null){
                            break;
                        }
                    }

                    // close
                    inputStream.close();
                    outputStream.flush();
                    outputStream.close();

                    // detect movie be remove
                    if(getInfoDownloadMovie(infoDownloadMovie) == null){
                        Log.e("TAG", "cancerDownload: ");
                        destinationPath.delete();
                        continue;
                    }
                    listMovie.poll();
                    updateNotification();
                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }
            }
            Log.e("TAG", "stopSelf: ");
            stopForeground(true);
            stopSelf();
        }).start();
    }

    public InfoDownloadMovie getInfoDownloadMovie(InfoDownloadMovie _infoDownloadMovie){
        for (InfoDownloadMovie infoDownloadMovie : listMovie) {
            if(infoDownloadMovie.getMovie().getId() == _infoDownloadMovie.getMovie().getId() && infoDownloadMovie.getEpisode().getNumber() == _infoDownloadMovie.getEpisode().getNumber()){
                return infoDownloadMovie;
            }
        }
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy Download Service: ");
    }

    private Notification createNotification(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channelId0", "channelName0", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("this is my channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channelId0");
        builder.setSmallIcon(R.drawable.download);
        builder.setContentTitle("Phim đang được tải");
        builder.setContentText("Số lượng phim đang tải " + listMovie.size());
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder.build();
    }

    private void updateNotification(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, createNotification());
    }
}
