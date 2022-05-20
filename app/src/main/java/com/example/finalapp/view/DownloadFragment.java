package com.example.finalapp.view;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.finalapp.R;
import com.example.finalapp.adapter.RecycleViewDownloadMovieListAdapter;
import com.example.finalapp.model.InfoDownloadMovie;
import com.example.finalapp.model.Movie;
import com.example.finalapp.service.MovieDownloadService;
import com.example.finalapp.viewmodel.DownloadViewModelFrag;

import java.util.ArrayList;

public class DownloadFragment extends Fragment {
    private Context context;
    private RecyclerView recycleViewDownloadMovieList;
    private RecycleViewDownloadMovieListAdapter recycleViewDownloadMovieListAdapter;
    private DownloadViewModelFrag downloadViewModelFrag;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_download, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find view
        recycleViewDownloadMovieList = view.findViewById(R.id.recycleViewDownloadMovieList);

        // init view
        recycleViewDownloadMovieList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        // init
        downloadViewModelFrag = new ViewModelProvider((MainActivity)context).get(DownloadViewModelFrag.class);
        downloadViewModelFrag.getListDownloadMovie().observe(getViewLifecycleOwner(), (ArrayList<InfoDownloadMovie> listDownloadMovie) -> {
            recycleViewDownloadMovieListAdapter = new RecycleViewDownloadMovieListAdapter(context, listDownloadMovie, getViewLifecycleOwner());
            recycleViewDownloadMovieList.setAdapter(recycleViewDownloadMovieListAdapter);
        });
        downloadViewModelFrag.loadListDownloadMovie();
    }
}