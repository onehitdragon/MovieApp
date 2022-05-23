package com.example.finalapp.MyVideoPlayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.finalapp.R;
import com.example.finalapp.model.Episode;
import com.example.finalapp.view.MainActivity;
import java.io.IOException;
import java.util.logging.Handler;

public class MyVideoPlayer {
    private Context context;
    private VideoView videoView;
    private View control, menuParent;
    private SeekBar seekBar;
    private ImageView btnStart;
    private int videoHeight;
    private boolean isOpeningFullScreen;
    private boolean isStartingVideo;
    private Thread thread;
    private Thread threadHideControl;

    public MyVideoPlayer(Context context, VideoView videoView, View control) {
        this.context = context;
        this.videoView = videoView;
        this.control = control;
        this.menuParent = ((MainActivity) context).findViewById(R.id.menu);
        this.seekBar = control.findViewById(R.id.seekBar);
        this.btnStart = control.findViewById(R.id.btnStart);
        isOpeningFullScreen = false;
        isStartingVideo = false;
    }

    public void initVideo(){
        // find view
        ImageView btnStart2 = control.findViewById(R.id.btnStart2);
        ImageView btnResize = control.findViewById(R.id.btnResize);
        TextView textViewTotalTime = control.findViewById(R.id.textViewTotalTime);
        ImageView btnSeekBackward = control.findViewById(R.id.btnSeekBackward);
        ImageView btnSeekForward = control.findViewById(R.id.btnSeekForward);

        // init
        View.OnClickListener btnStartClick = (View view) -> {
            if(isStartingVideo){
                stopVideo();
                btnStart.setImageResource(R.drawable.pausing_icon);
                btnStart2.setImageResource(R.drawable.pausing_icon);
            }
            else{
                startVideo();
                btnStart.setImageResource(R.drawable.playing_icon);
                btnStart2.setImageResource(R.drawable.playing_icon);
            }
        };

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // init
                videoHeight = videoView.getHeight();
                resizeControlVideo(videoHeight);
                int duration = videoView.getDuration();
                seekBar.setMax(duration);
                textViewTotalTime.setText(millisecondToTime(duration));

                // thread
                createThread();
                thread.start();
                createThreadHideControl();
                threadHideControl.start();

                // event
                btnStart.setOnClickListener(btnStartClick);
                btnStart2.setOnClickListener(btnStartClick);
                btnResize.setOnClickListener((View view) -> {
                    control.performClick();
                    if(isOpeningFullScreen){
                        zoomInVideo();
                    }
                    else{
                        zoomOutVideo();
                    }
                });
                btnSeekBackward.setOnClickListener((View view) -> {
                    videoView.seekTo(videoView.getCurrentPosition() - 10000);
                });
                btnSeekForward.setOnClickListener((View view) -> {
                    videoView.seekTo(videoView.getCurrentPosition() + 10000);
                });
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        videoView.seekTo(seekBar.getProgress());
                    }
                });
                control.setOnClickListener((View view) -> {
                    control.setAlpha(1);
                    threadHideControl.interrupt();
                    createThreadHideControl();
                    threadHideControl.start();
                });

                //
                btnStart.performClick();
            }
        });
        videoView.setOnCompletionListener((MediaPlayer mediaPlayer) -> {
            mediaPlayer.release();
        });
    }

    public void zoomInVideo(){
        float videoHeight = videoView.getHeight();
        ViewGroup.LayoutParams videoLayoutParams = videoView.getLayoutParams();
        videoLayoutParams.height = (int) videoHeight;
        videoView.setLayoutParams(videoLayoutParams);
        zoomInControl();
        menuParent.setVisibility(View.VISIBLE);
        // add title
        ((MainActivity)context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        isOpeningFullScreen = false;
    }

    public void zoomOutVideo(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((MainActivity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        ViewGroup.LayoutParams videoLayoutParams = videoView.getLayoutParams();
        videoLayoutParams.width = width;
        videoLayoutParams.height = height;
        videoView.setLayoutParams(videoLayoutParams);
        resizeControlVideo(height);
        menuParent.setVisibility(View.GONE);
        // remove title
        ((MainActivity)context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        isOpeningFullScreen = true;
    }

    private void zoomInControl(){
        resizeControlVideo(videoHeight);
    }

    private void resizeControlVideo(int height){
        ViewGroup.LayoutParams layoutParams = control.getLayoutParams();
        layoutParams.height = height;
        control.setLayoutParams(layoutParams);
    }

    private void stopVideo(){
        videoView.pause();
        isStartingVideo = false;
    }

    private void startVideo(){
        videoView.start();
        isStartingVideo = true;
        if(!thread.isAlive()){
            createThread();
            thread.start();
        }
    }

    private String millisecondToTime(int duration){
        String time = "";
        int hour = duration / 1000 / 60 / 60;
        if(hour != 0) {
            if(hour / 10 == 0){
                time+= "0" + hour + ":";
            }
            else{
                time+= hour + ":";
            }
        }
        int minute = Math.abs ((60 * hour) - (duration / 1000 / 60));
        if(minute != 0) {
            if(minute / 10 == 0){
                time += "0" + minute + ":";
            }
            else{
                time += minute + ":";
            }
        }
        int second = Math.abs ((60 * minute) - (duration / 1000));
        if(second != 0) {
            if(second / 10 == 0){
                time += "0" + second;
            }
            else{
                time += second;
            }
        }
        if(minute == 0 && hour == 0){
            time = "00:" + time;
        }
        if(minute == 0 && hour != 0){
            time = time + "00";
        }
        if(second == 0){
            time = time + "00";
        }

        return time;
    }

    private void createThread(){
        TextView textViewCountTime = control.findViewById(R.id.textViewCountTime);
        seekBar.setProgress(0);
        this.thread = new Thread(() -> {
            int duration = videoView.getDuration();
            int current = 0;
            do{
                current = videoView.getCurrentPosition();
                int finalCurrent = current;
                videoView.post(() -> {
                    textViewCountTime.setText(millisecondToTime(finalCurrent));
                    if(!seekBar.isInTouchMode()){
                        seekBar.setProgress(finalCurrent);
                    }
                });
            }
            while (current < duration);
            btnStart.post(() -> {
                if(isStartingVideo){
                    btnStart.performClick();
                }
            });
        });
    }

    private void createThreadHideControl(){
        this.threadHideControl = new Thread(() -> {
            try {
                Thread.sleep(5000);
                control.post(() -> {
                   control.setAlpha(0);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void play(Episode episode){
        if(thread != null && thread.isAlive()){
            thread.interrupt();
        }
        if(threadHideControl != null && threadHideControl.isAlive()){
            threadHideControl.interrupt();
        }
        if(episode.getDestinationPathSaved() == null){
            videoView.setVideoPath(episode.getSourceUrl());
        }
        else{
            Uri uri = Uri.fromFile(episode.getDestinationPathSaved());
            videoView.setVideoURI(uri);
            Log.e("TAG", "play storage: " + uri.getPath());
        }
    }
}
