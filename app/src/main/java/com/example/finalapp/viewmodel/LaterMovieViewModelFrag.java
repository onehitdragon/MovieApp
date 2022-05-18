package com.example.finalapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.finalapp.localrepository.HistoryMovieRepository;
import com.example.finalapp.localrepository.LaterMovieRepository;
import com.example.finalapp.model.HistoryMovie;
import com.example.finalapp.model.Movie;

import java.util.ArrayList;

public class LaterMovieViewModelFrag extends AndroidViewModel {
    private LaterMovieRepository laterMovieRepository;
    private MutableLiveData<ArrayList<Movie>> listLaterMovie;

    public MutableLiveData<ArrayList<Movie>> getListLaterMovie() {
        return listLaterMovie;
    }

    public void setListLaterMovie(MutableLiveData<ArrayList<Movie>> listLaterMovie) {
        this.listLaterMovie = listLaterMovie;
    }

    public LaterMovieViewModelFrag(@NonNull Application application) {
        super(application);
        laterMovieRepository = new LaterMovieRepository(application);
        listLaterMovie = new MutableLiveData<>();
    }

    public void loadLaterMovieList(){
        ArrayList<Movie> listMovieResult = laterMovieRepository.get();
        listLaterMovie.setValue(listMovieResult);
    }

    public void deleteMovieFromLater(Movie movie){
        laterMovieRepository.delete(movie);
        loadLaterMovieList();
    }
}
