package com.example.finalapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.example.finalapp.localrepository.HistoryMovieRepository;
import com.example.finalapp.model.Episode;
import com.example.finalapp.model.HistoryMovie;
import com.example.finalapp.model.Movie;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MovieWatchingViewModelFrag extends AndroidViewModel {
    private Episode currentEpisode;
    private HistoryMovieRepository historyMovieRepository;

    public Episode getCurrentEpisode() {
        return currentEpisode;
    }

    public void setCurrentEpisode(Episode currentEpisode) {
        this.currentEpisode = currentEpisode;
    }

    public MovieWatchingViewModelFrag(Application application) {
        super(application);
        historyMovieRepository = new HistoryMovieRepository(application);
    }

    public void addMovieToHistory(Movie movie){
        Calendar previousTime = Calendar.getInstance();
        HistoryMovie historyMovie = new HistoryMovie(movie, previousTime);
        historyMovieRepository.insert(historyMovie);
    }
}
