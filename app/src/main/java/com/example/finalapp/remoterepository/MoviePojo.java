package com.example.finalapp.remoterepository;
import java.util.ArrayList;

public class MoviePojo {
    public int id;
    public DirectorPojo director;
    public ArrayList<ActorPojo> listActor;
    public ArrayList<GenrePojo> listGenre;
    public ArrayList<EpisodePojo> listEpisode;
    public String title;
    public String engTitle;
    public String avatarUrl;
    public int releaseYear;
    public String country;
    public float rating;
    public String content;
    public int movieLength;
}
