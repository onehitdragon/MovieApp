package com.example.finalapp.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.example.finalapp.MyVideoPlayer.MyVideoPlayer;
import com.example.finalapp.R;
import com.example.finalapp.adapter.RecycleViewActorListAdapter;
import com.example.finalapp.adapter.RecycleViewEpisodeListAdapter;
import com.example.finalapp.databinding.FragmentMovieWatchingBinding;
import com.example.finalapp.model.Episode;
import com.example.finalapp.model.Movie;
import com.example.finalapp.mydialog.MyDialog;
import com.example.finalapp.mydialog.MyDialogFactory;
import com.example.finalapp.service.MovieDownloadService;
import com.example.finalapp.viewmodel.HomeViewModelFrag;
import com.example.finalapp.viewmodel.MovieWatchingViewModelFrag;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class MovieWatchingFragment extends Fragment {
    private Context context;
    private FragmentMovieWatchingBinding binding;
    private HomeViewModelFrag homeViewModelFrag;
    private MovieWatchingViewModelFrag movieWatchingViewModelFrag;
    private Movie movie;
    private RecyclerView recycleViewActorList, recycleViewEpisodeList;
    private RecycleViewActorListAdapter recycleViewActorListAdapter;
    private RecycleViewEpisodeListAdapter recycleViewEpisodeListAdapter;
    private MyVideoPlayer myVideoPlayer;
    private View btnAddLaterMovie, btnDownloadMovie;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_watching, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find view
        recycleViewActorList = view.findViewById(R.id.recycleViewActorList);
        recycleViewEpisodeList = view.findViewById(R.id.recycleViewEpisodeList);
        btnAddLaterMovie = view.findViewById(R.id.btnAddLaterMovie);
        btnDownloadMovie = view.findViewById(R.id.btnDownloadMovie);

        // init view
        myVideoPlayer = new MyVideoPlayer(context, view.findViewById(R.id.videoView), view.findViewById(R.id.control));
        myVideoPlayer.initVideo();
        recycleViewActorList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP);
        recycleViewEpisodeList.setLayoutManager(flexboxLayoutManager);

        // init
        homeViewModelFrag = new ViewModelProvider((MainActivity) context).get(HomeViewModelFrag.class);
        movie = homeViewModelFrag.getCurrentMovie();
        binding.setMovie(movie);
        recycleViewActorListAdapter = new RecycleViewActorListAdapter(context, movie.getListActor());
        recycleViewActorList.setAdapter(recycleViewActorListAdapter);
        movieWatchingViewModelFrag = new ViewModelProvider(this).get(MovieWatchingViewModelFrag.class);
        if(movieWatchingViewModelFrag.getCurrentEpisode() == null){
            movieWatchingViewModelFrag.setCurrentEpisode(movie.getListEpisode().get(0));
        }
        myVideoPlayer.playFromUrl(movieWatchingViewModelFrag.getCurrentEpisode().getSourceUrl());
        OnEpisodeClick onEpisodeClick = (Episode episode) -> {
            movieWatchingViewModelFrag.setCurrentEpisode(episode);
            myVideoPlayer.playFromUrl(episode.getSourceUrl());
        };
        recycleViewEpisodeListAdapter = new RecycleViewEpisodeListAdapter(context, movie.getListEpisode(), movieWatchingViewModelFrag.getCurrentEpisode(), onEpisodeClick);
        recycleViewEpisodeList.setAdapter(recycleViewEpisodeListAdapter);
        movieWatchingViewModelFrag.addMovieToHistory(movie);

        // event
        btnAddLaterMovie.setOnClickListener((View v) -> {
            movieWatchingViewModelFrag.addMovieToLater(movie);
            MyDialog myDialog = MyDialogFactory.createAddedLaterMovie(context);
            myDialog.show();
        });
        btnDownloadMovie.setOnClickListener((View v) -> {
            MyDialog myDialog;
            if(movieWatchingViewModelFrag.addMovieToDownload(movie)){
                myDialog = MyDialogFactory.createAddedDownload(context);
                Intent intent = new Intent(context, MovieDownloadService.class);
                intent.putExtra("movieUrl", "https://pouch.jumpshare.com/preview/drM4im3xxR1gMoXUX1AadF-Owb0FvIlxHDUSO_PFadnP6nzLUlN_VVvDz9gIhTJoPfyRWQpc1GqtO8as-dNEhU-4cR7LGgEXU1e62FZ9N3403FfWQWXBRSa4d0QPPgvT.mp4");
                context.startService(intent);
                context.bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        Log.e("TAG", "onServiceConnected: ");
                        MovieDownloadService movieDownloadService = ((MovieDownloadService.MyBinder)iBinder).getService();
                        movieDownloadService.getProgress().observe(getViewLifecycleOwner(), (Integer progress) -> {
                            Log.e("TAG", "onServiceConnected: " + progress);
                        });
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName componentName) {
                        Log.e("TAG", "onServiceDisconnected: ");
                    }
                }, Context.BIND_AUTO_CREATE);
            }
            else{
                myDialog = MyDialogFactory.createAddedDownloadFail(context);
            }
            myDialog.show();
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            myVideoPlayer.zoomOutVideo();
        }
        else{
            myVideoPlayer.zoomInVideo();
        }
    }

    public interface OnEpisodeClick{
        void click(Episode episode);
    }
}