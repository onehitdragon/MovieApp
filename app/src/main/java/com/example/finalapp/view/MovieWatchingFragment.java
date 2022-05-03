package com.example.finalapp.view;

import android.graphics.ColorFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.example.finalapp.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class MovieWatchingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_watching, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VideoView videoView = view.findViewById(R.id.videoView);
        SeekBar progressBar = view.findViewById(R.id.seekBar);

        String url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4";
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                float videoHeight = videoView.getHeight();
                View control = view.findViewById(R.id.control);
                LayoutParams layoutParams = control.getLayoutParams();
                layoutParams.height = (int) videoHeight;
                control.setLayoutParams(layoutParams);
                Log.e("", "height: " + videoHeight);
            }
        });
    }
}