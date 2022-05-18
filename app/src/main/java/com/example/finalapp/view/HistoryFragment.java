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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalapp.R;
import com.example.finalapp.adapter.RecycleViewHistoryListAdapter;
import com.example.finalapp.model.HistoryMovie;
import com.example.finalapp.viewmodel.HistoryViewModelFrag;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    private Context context;
    private RecyclerView recycleViewHistoryList, recycleViewLaterMovieList;
    private HistoryViewModelFrag historyViewModelFrag;
    private RecycleViewHistoryListAdapter recycleViewHistoryListAdapter;

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
        historyViewModelFrag.getListHistoryMovie().observe(getViewLifecycleOwner(), (ArrayList<HistoryMovie> listHistoryMovie) -> {
            Log.e("TAG", "onViewCreated: " +  listHistoryMovie.size());
            recycleViewHistoryListAdapter = new RecycleViewHistoryListAdapter(context, listHistoryMovie);
            recycleViewHistoryList.setAdapter(recycleViewHistoryListAdapter);
        });
        historyViewModelFrag.loadHistoryMovie();
    }
}