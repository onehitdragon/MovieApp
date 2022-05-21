package com.example.finalapp.model;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.io.File;

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

    public File getDestinationPath(Context context){
        File[] files = context.getExternalCacheDirs();
        File internalStorage = files[0];
        File sdCard = files[1];
        File destination = sdCard != null ? sdCard : internalStorage;
        String[] extension = episode.getSourceUrl().split("\\.");
        String fileName = movie.getId() + "-" + episode.getNumber() + "." + extension[extension.length - 1];
        String outputPath = destination.getPath() + "/" + fileName;

        return new File(outputPath);
    }
}
