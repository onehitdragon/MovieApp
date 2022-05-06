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
import com.example.finalapp.viewmodel.HomeViewModelFrag;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ImageView imageViewAvatar;
    private HomeViewModelFrag homeViewModelFrag;
    private RecyclerView recycleViewNewestList, recycleViewGenreMenu, recycleViewGenreMovieList;
    private RecycleViewNewestMovieListAdapter recycleViewNewestMovieListAdapter;
    private RecycleViewGenreMenuAdapter recycleViewGenreMenuAdapter;
    private CardView currentCardViewGenreMenu;
    private RecycleViewGenreMovieListAdapter recycleViewGenreMovieListAdapter;

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
        recycleViewNewestList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleViewGenreMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleViewGenreMovieList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        if(savedInstanceState != null){
            restoreView(savedInstanceState);
        }

        // init
        Glide.with(this).load(R.drawable.bg3).circleCrop().into(imageViewAvatar);
        homeViewModelFrag = new ViewModelProvider(this).get(HomeViewModelFrag.class);
        homeViewModelFrag.getListNewestMovie().observe(getViewLifecycleOwner(), (ArrayList<Movie> listNewestMovie) -> {
            recycleViewNewestMovieListAdapter = new RecycleViewNewestMovieListAdapter(getActivity(), listNewestMovie);
            recycleViewNewestList.setAdapter(recycleViewNewestMovieListAdapter);
        });
        OnChangeColorCardViewGenreMenu onChangeColorCardViewGenreMenu = (CardView cardView) -> {
            if(cardView == currentCardViewGenreMenu) return;
            cardView.setCardBackgroundColor(Color.parseColor("#414a4c"));
            if(currentCardViewGenreMenu != null){
                currentCardViewGenreMenu.setCardBackgroundColor(getResources().getColor(R.color.transparent));
            }
            currentCardViewGenreMenu = cardView;
        };
        OnGenreMenuItemClickListener onGenreMenuItemClickListener = (CardView cardView, Genre genre) -> {
            onChangeColorCardViewGenreMenu.change(cardView);
            homeViewModelFrag.loadListMovieByGenre(genre);
        };
        homeViewModelFrag.getListGenre().observe(getViewLifecycleOwner(), (ArrayList<Genre> listGenre) -> {
            if(homeViewModelFrag.getCurrentGenre() == null){
                homeViewModelFrag.loadListMovieByGenre(listGenre.get(0));
            }
            recycleViewGenreMenuAdapter = new RecycleViewGenreMenuAdapter(getActivity(), listGenre, onGenreMenuItemClickListener, onChangeColorCardViewGenreMenu, homeViewModelFrag.getCurrentGenre());
            recycleViewGenreMenu.setAdapter(recycleViewGenreMenuAdapter);
        });
        homeViewModelFrag.getListMovieByGenre().observe(getViewLifecycleOwner(), (ArrayList<Movie> listMovieByGenre) -> {
            recycleViewGenreMovieListAdapter = new RecycleViewGenreMovieListAdapter(getActivity(), listMovieByGenre, homeViewModelFrag.getCurrentGenre());
            recycleViewGenreMovieList.setAdapter(recycleViewGenreMovieListAdapter);
        });

        // event
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int firstVisiblePositionRecycleViewNewestList = 0;
        if(recycleViewNewestList.getLayoutManager() != null){
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
        void click(CardView cardView, Genre genre);
    }

    public interface OnChangeColorCardViewGenreMenu{
        void change(CardView cardView);
    }
}