package com.example.finalapp.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalapp.R;
import com.example.finalapp.adapter.RecycleViewGenreMenuAdapter;
import com.example.finalapp.adapter.RecycleViewGenreMovieListAdapter;
import com.example.finalapp.adapter.RecycleViewNewestMovieListAdapter;
import com.example.finalapp.model.Genre;
import com.example.finalapp.model.Movie;
import com.example.finalapp.viewmodel.HomeViewModelFrag;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Context context;
    private ImageView imageViewAvatar;
    private HomeViewModelFrag homeViewModelFrag;
    private RecyclerView recycleViewNewestList, recycleViewGenreMenu, recycleViewGenreMovieList;
    private RecycleViewNewestMovieListAdapter recycleViewNewestMovieListAdapter;
    private RecycleViewGenreMenuAdapter recycleViewGenreMenuAdapter;
    private RecycleViewGenreMovieListAdapter recycleViewGenreMovieListAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find view
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
        recycleViewNewestList = view.findViewById(R.id.recycleViewNewestList);
        recycleViewGenreMenu = view.findViewById(R.id.recycleViewGenreMenu);
        recycleViewGenreMovieList = view.findViewById(R.id.recycleViewGenreMovieList);

        // init view
        recycleViewNewestList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recycleViewGenreMenu.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recycleViewGenreMovieList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        if(savedInstanceState != null){
            restoreView(savedInstanceState);
        }

        // init
        Glide.with(this).load(R.drawable.bg3).circleCrop().into(imageViewAvatar);
        homeViewModelFrag = new ViewModelProvider((ViewModelStoreOwner) context).get(HomeViewModelFrag.class);
        OnMovieClick onMovieClick = (Movie movie) -> {
            homeViewModelFrag.setCurrentMovie(movie);
            ((MainActivity)context).openMovieIntroFragment();
        };
        homeViewModelFrag.getListNewestMovie().observe(getViewLifecycleOwner(), (ArrayList<Movie> listNewestMovie) -> {
            recycleViewNewestMovieListAdapter = new RecycleViewNewestMovieListAdapter(context, listNewestMovie, onMovieClick);
            recycleViewNewestList.setAdapter(recycleViewNewestMovieListAdapter);
        });
        OnGenreMenuItemClickListener onGenreMenuItemClickListener = (Genre genre) -> {
            homeViewModelFrag.loadListMovieByGenre(genre);
        };
        homeViewModelFrag.getListGenre().observe(getViewLifecycleOwner(), (ArrayList<Genre> listGenre) -> {
            if(homeViewModelFrag.getCurrentGenre() == null){
                homeViewModelFrag.loadListMovieByGenre(listGenre.get(0));
            }
            recycleViewGenreMenuAdapter = new RecycleViewGenreMenuAdapter(context, listGenre, onGenreMenuItemClickListener, homeViewModelFrag.getCurrentGenre());
            recycleViewGenreMenu.setAdapter(recycleViewGenreMenuAdapter);
        });
        homeViewModelFrag.getListMovieByGenre().observe(getViewLifecycleOwner(), (ArrayList<Movie> listMovieByGenre) -> {
            recycleViewGenreMovieListAdapter = new RecycleViewGenreMovieListAdapter(context, listMovieByGenre, homeViewModelFrag.getCurrentGenre(), onMovieClick);
            recycleViewGenreMovieList.setAdapter(recycleViewGenreMovieListAdapter);
        });

        // event
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int firstVisiblePositionRecycleViewNewestList = 0;
        if(recycleViewNewestList != null && recycleViewNewestList.getLayoutManager() != null){
            firstVisiblePositionRecycleViewNewestList = ((LinearLayoutManager)recycleViewNewestList.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }
        outState.putInt("firstVisiblePositionRecycleViewNewestList", firstVisiblePositionRecycleViewNewestList);
    }

    private void restoreView(Bundle savedInstanceState){
        if(recycleViewNewestList.getLayoutManager() != null){
            int firstVisiblePositionRecycleViewNewestList = savedInstanceState.getInt("firstVisiblePositionRecycleViewNewestList");
            recycleViewNewestList.getLayoutManager().scrollToPosition(firstVisiblePositionRecycleViewNewestList);
        }
    }

    public interface OnGenreMenuItemClickListener{
        void click(Genre genre);
    }

    public interface OnMovieClick{
        void click(Movie movie);
    }
}