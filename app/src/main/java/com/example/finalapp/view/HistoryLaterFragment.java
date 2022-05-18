package com.example.finalapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.finalapp.R;
import com.example.finalapp.adapter.RecycleViewHistoryListAdapter;
import com.example.finalapp.adapter.RecycleViewLaterMovieListAdapter;
import com.example.finalapp.localrepository.HistoryMovieRepository;
import com.example.finalapp.model.HistoryMovie;
import com.example.finalapp.model.Movie;
import com.example.finalapp.viewmodel.HistoryViewModelFrag;
import com.example.finalapp.viewmodel.HomeViewModelFrag;
import com.example.finalapp.viewmodel.LaterMovieViewModelFrag;

import java.util.ArrayList;

public class HistoryLaterFragment extends Fragment {
    private Context context;
    private RecyclerView recycleViewHistoryList, recycleViewLaterMovieList;
    private HistoryViewModelFrag historyViewModelFrag;
    private LaterMovieViewModelFrag laterMovieViewModelFrag;
    private RecycleViewHistoryListAdapter recycleViewHistoryListAdapter;
    private RecycleViewLaterMovieListAdapter recycleViewLaterMovieListAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find view
        recycleViewHistoryList = view.findViewById(R.id.recycleViewHistoryList);
        recycleViewLaterMovieList = view.findViewById(R.id.recycleViewLaterMovieList);

        // init view
        recycleViewHistoryList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recycleViewLaterMovieList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        // init
        historyViewModelFrag = new ViewModelProvider((MainActivity) context).get(HistoryViewModelFrag.class);
        HomeViewModelFrag homeViewModelFrag = new ViewModelProvider((MainActivity) context).get(HomeViewModelFrag.class);
        Movie.OnMovieClick onMovieClick = (Movie movie) -> {
            homeViewModelFrag.setCurrentMovie(movie);
            ((MainActivity) context).openMovieIntroFragment();
        };
        historyViewModelFrag.getListHistoryMovie().observe(getViewLifecycleOwner(), (ArrayList<HistoryMovie> listHistoryMovie) -> {
            recycleViewHistoryListAdapter = new RecycleViewHistoryListAdapter(context, listHistoryMovie, onMovieClick);
            recycleViewHistoryList.setAdapter(recycleViewHistoryListAdapter);
        });
        historyViewModelFrag.loadHistoryMovieList();

        laterMovieViewModelFrag = new ViewModelProvider((MainActivity) context).get(LaterMovieViewModelFrag.class);
        laterMovieViewModelFrag.getListLaterMovie().observe(getViewLifecycleOwner(), (ArrayList<Movie> listLaterMovie) -> {
            recycleViewLaterMovieListAdapter = new RecycleViewLaterMovieListAdapter(context, listLaterMovie, onMovieClick, laterMovieViewModelFrag);
            recycleViewLaterMovieList.setAdapter(recycleViewLaterMovieListAdapter);
        });
        laterMovieViewModelFrag.loadLaterMovieList();

        //
    }
}