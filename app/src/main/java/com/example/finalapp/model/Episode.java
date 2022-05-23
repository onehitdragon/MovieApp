package com.example.finalapp.model;

import com.example.finalapp.remoterepository.BaseUrl;
import com.example.finalapp.remoterepository.EpisodePojo;

import java.io.File;

public class Episode {
    private int number;
    private String sourceUrl;
    private File destinationPathSaved;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public File getDestinationPathSaved() {
        return destinationPathSaved;
    }

    public void setDestinationPathSaved(File destinationPathSaved) {
        this.destinationPathSaved = destinationPathSaved;
    }

    public Episode(int number, String sourceUrl) {
        this.number = number;
        this.sourceUrl = BaseUrl.URL + sourceUrl;
    }

    public static Episode convertPojo(EpisodePojo episodePojo){
        return new Episode(episodePojo.number, episodePojo.sourceUrl);
    }
}
