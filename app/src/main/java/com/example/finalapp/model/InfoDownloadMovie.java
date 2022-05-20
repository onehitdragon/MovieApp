package com.example.finalapp.model;

import androidx.lifecycle.MutableLiveData;

public class InfoDownloadMovie{
    private Movie movie;
    private Episode episode;
    private MutableLiveData<Integer> progress;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public void setProgress(MutableLiveData<Integer> progress) {
        this.progress = progress;
    }

    public MutableLiveData<Integer> getProgress() {
        return progress;
    }

    public InfoDownloadMovie(Movie movie, Episode episode) {
        this.movie = movie;
        this.episode = episode;
        progress = new MutableLiveData<>();
    }
}
