package com.example.finalapp.view;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalapp.R;
import com.example.finalapp.adapter.RecycleViewGenreOfMovieAdapter;
import com.example.finalapp.databinding.FragmentMovieIntroBinding;
import com.example.finalapp.model.Movie;
import com.example.finalapp.viewmodel.HomeViewModelFrag;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

public class MovieIntroFragment extends Fragment {
    private Context context;
    private HomeViewModelFrag homeViewModelFrag;
    private Button btnBack, btnWatchMovie;
    private ImageView imageViewAvatar;
    private FragmentMovieIntroBinding binding;
    private RecyclerView recycleViewGenreOfMovie;
    private RecycleViewGenreOfMovieAdapter recycleViewGenreOfMovieAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_intro, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find view
        btnBack = view.findViewById(R.id.btnBack);
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
        recycleViewGenreOfMovie = view.findViewById(R.id.recycleViewGenreOfMovie);
        btnWatchMovie = view.findViewById(R.id.btnWatchMovie);

        // init view
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP);
        recycleViewGenreOfMovie.setLayoutManager(flexboxLayoutManager);

        // init
        homeViewModelFrag = new ViewModelProvider((MainActivity)context).get(HomeViewModelFrag.class);
        Movie movie = homeViewModelFrag.getCurrentMovie();
        Glide.with(context).load(movie.getAvatarUrl()).into(imageViewAvatar);
        binding.setMovie(movie);
        recycleViewGenreOfMovieAdapter = new RecycleViewGenreOfMovieAdapter(context, movie.getListGenre());
        recycleViewGenreOfMovie.setAdapter(recycleViewGenreOfMovieAdapter);

        // event
        btnBack.setOnClickListener((View v) -> {
            ((MainActivity) context).closeFragment("MovieIntroFragment");
        });
        btnWatchMovie.setOnClickListener((View v) -> {
            ((MainActivity)context).openMovieWatchingFragment();
        });
    }
}