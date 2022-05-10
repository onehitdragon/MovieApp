package com.example.finalapp.view;

import android.content.Context;
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

        // init view
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
        OnEpisodeClick onEpisodeClick = (Episode episode) -> {
            movieWatchingViewModelFrag.setCurrentEpisode(episode);
        };
        recycleViewEpisodeListAdapter = new RecycleViewEpisodeListAdapter(context, movie.getListEpisode(), movieWatchingViewModelFrag.getCurrentEpisode(), onEpisodeClick);
        recycleViewEpisodeList.setAdapter(recycleViewEpisodeListAdapter);

        //
        myVideoPlayer = new MyVideoPlayer(context, view.findViewById(R.id.videoView), view.findViewById(R.id.control));
        myVideoPlayer.initVideo();
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