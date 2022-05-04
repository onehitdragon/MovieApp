package com.example.finalapp.view;

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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalapp.R;
import com.example.finalapp.adapter.RecycleViewGenreMenuAdapter;
import com.example.finalapp.adapter.RecycleViewGenreMovieListAdapter;
import com.example.finalapp.adapter.RecycleViewNewestMovieListAdapter;
import com.example.finalapp.model.Genre;
import com.example.finalapp.model.Movie;
import com.example.finalapp.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private ImageView imageViewAvatar;
    private HomeViewModel homeViewModel;
    private RecyclerView recycleViewNewestList, recycleViewGenreMenu, recycleViewGenreMovieList;
    private RecycleViewNewestMovieListAdapter recycleViewNewestMovieListAdapter;
    private RecycleViewGenreMenuAdapter recycleViewGenreMenuAdapter;
    private CardView currentCardViewGenreMenu;
    private RecycleViewGenreMovieListAdapter recycleViewGenreMovieListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find view
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
        recycleViewNewestList = view.findViewById(R.id.recycleViewNewestList);
        recycleViewGenreMenu = view.findViewById(R.id.recycleViewGenreMenu);
        recycleViewGenreMovieList = view.findViewById(R.id.recycleViewGenreMovieList);
        recycleViewNewestList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleViewGenreMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleViewGenreMovieList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        // init
        Glide.with(this).load(R.drawable.bg3).circleCrop().into(imageViewAvatar);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getListNewestMovie().observe(getViewLifecycleOwner(), (ArrayList<Movie> listNewestMovie) -> {
            recycleViewNewestMovieListAdapter = new RecycleViewNewestMovieListAdapter(getActivity(), listNewestMovie);
            recycleViewNewestList.setAdapter(recycleViewNewestMovieListAdapter);
        });
        OnGenreMenuItemClickListener onGenreMenuItemClickListener = (CardView cardView, Genre genre) -> {
            changeColorCardViewGenreMenu(cardView);
            homeViewModel.loadListMovieByGenre(genre);
        };
        homeViewModel.getListGenre().observe(getViewLifecycleOwner(), (ArrayList<Genre> listGenre) -> {
            recycleViewGenreMenuAdapter = new RecycleViewGenreMenuAdapter(getActivity(), listGenre, onGenreMenuItemClickListener);
            recycleViewGenreMenu.setAdapter(recycleViewGenreMenuAdapter);
            if(homeViewModel.getCurrentGenre() == null){
                homeViewModel.loadListMovieByGenre(listGenre.get(0));
            }
        });
        homeViewModel.getListMovieByGenre().observe(getViewLifecycleOwner(), (ArrayList<Movie> listMovieByGenre) -> {
            recycleViewGenreMovieListAdapter = new RecycleViewGenreMovieListAdapter(getActivity(), listMovieByGenre, homeViewModel.getCurrentGenre());
            recycleViewGenreMovieList.setAdapter(recycleViewGenreMovieListAdapter);
        });
    }

    public interface OnGenreMenuItemClickListener{
        void click(CardView cardView, Genre genre);
    }
    private void changeColorCardViewGenreMenu(CardView cardView){
        if(cardView == currentCardViewGenreMenu) return;
        cardView.setCardBackgroundColor(Color.parseColor("#414a4c"));
        if(currentCardViewGenreMenu != null){
            currentCardViewGenreMenu.setCardBackgroundColor(getResources().getColor(R.color.transparent));
        }
        currentCardViewGenreMenu = cardView;
    }
}