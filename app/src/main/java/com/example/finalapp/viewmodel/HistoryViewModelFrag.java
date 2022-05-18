package com.example.finalapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.finalapp.localrepository.HistoryMovieRepository;
import com.example.finalapp.model.HistoryMovie;

import java.util.ArrayList;

public class HistoryViewModelFrag extends AndroidViewModel {
    private HistoryMovieRepository historyMovieRepository;
    private MutableLiveData<ArrayList<HistoryMovie>> listHistoryMovie;

    public MutableLiveData<ArrayList<HistoryMovie>> getListHistoryMovie() {
        return listHistoryMovie;
    }

    public void setListHistoryMovie(MutableLiveData<ArrayList<HistoryMovie>> listHistoryMovie) {
        this.listHistoryMovie = listHistoryMovie;
    }

    public HistoryViewModelFrag(@NonNull Application application) {
        super(application);
        historyMovieRepository = new HistoryMovieRepository(application);
        listHistoryMovie = new MutableLiveData<>();
    }

    public void loadHistoryMovie(){
        ArrayList<HistoryMovie> listMovieResult = historyMovieRepository.get();
        listHistoryMovie.setValue(listMovieResult);
    }
}
