package com.example.finalapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.finalapp.localrepository.DownloadMovieRepository;

public class DownloadViewModelFrag extends AndroidViewModel {
    private DownloadMovieRepository downloadMovieRepository;

    public DownloadViewModelFrag(@NonNull Application application) {
        super(application);
        downloadMovieRepository = new DownloadMovieRepository(application);
    }
}
