package com.example.finalapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.finalapp.localrepository.DownloadMovieRepository;
import com.example.finalapp.model.HistoryMovie;
import com.example.finalapp.model.InfoDownloadMovie;
import com.example.finalapp.model.Movie;

import java.util.ArrayList;

public class DownloadViewModelFrag extends AndroidViewModel {
    private DownloadMovieRepository downloadMovieRepository;
    private MutableLiveData<ArrayList<InfoDownloadMovie>> listDownloadMovie;

    public MutableLiveData<ArrayList<InfoDownloadMovie>> getListDownloadMovie() {
        return listDownloadMovie;
    }

    public void setListDownloadMovie(MutableLiveData<ArrayList<InfoDownloadMovie>> listDownloadMovie) {
        this.listDownloadMovie = listDownloadMovie;
    }

    public DownloadViewModelFrag(@NonNull Application application) {
        super(application);
        downloadMovieRepository = new DownloadMovieRepository(application);
        listDownloadMovie = new MutableLiveData<>();
    }

    public void loadListDownloadMovie(){
        ArrayList<InfoDownloadMovie> listMovieResult = downloadMovieRepository.get();
        listDownloadMovie.setValue(listMovieResult);
    }

    public void deleteFromDownload(InfoDownloadMovie infoDownloadMovie){
        downloadMovieRepository.delete(infoDownloadMovie);
        loadListDownloadMovie();
    }
}
