package com.example.finalapp.viewmodel;

import androidx.lifecycle.ViewModel;
import com.example.finalapp.model.Episode;


public class MovieWatchingViewModelFrag extends ViewModel {
    private Episode currentEpisode;

    public Episode getCurrentEpisode() {
        return currentEpisode;
    }

    public void setCurrentEpisode(Episode currentEpisode) {
        this.currentEpisode = currentEpisode;
    }

    public MovieWatchingViewModelFrag() {
    }
}
