package com.example.finalapp.model;

import com.example.finalapp.remoterepository.ActorPojo;
import com.example.finalapp.remoterepository.BaseUrl;
import com.example.finalapp.remoterepository.EpisodePojo;
import com.example.finalapp.remoterepository.GenrePojo;
import com.example.finalapp.remoterepository.MoviePojo;
import com.example.finalapp.remoterepository.RetrofitClient;

import java.util.ArrayList;

public class Movie {
    private int id;
    private Director director;
    private ArrayList<Actor> listActor;
    private ArrayList<Genre> listGenre;
    private ArrayList<Episode> listEpisode;
    private String title;
    private String engTitle;
    private String avatarUrl;
    private int releaseYear;
    private String country;
    private float rating;
    private String content;
    private int movieLength;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public ArrayList<Actor> getListActor() {
        return listActor;
    }

    public void setListActor(ArrayList<Actor> listActor) {
        this.listActor = listActor;
    }

    public ArrayList<Genre> getListGenre() {
        return listGenre;
    }

    public void setListGenre(ArrayList<Genre> listGenre) {
        this.listGenre = listGenre;
    }

    public ArrayList<Episode> getListEpisode() {
        return listEpisode;
    }

    public void setListEpisode(ArrayList<Episode> listEpisode) {
        this.listEpisode = listEpisode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEngTitle() {
        return engTitle;
    }

    public void setEngTitle(String engTitle) {
        this.engTitle = engTitle;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(int movieLength) {
        this.movieLength = movieLength;
    }

    public Movie(int id, Director director, ArrayList<Actor> listActor, ArrayList<Genre> listGenre, ArrayList<Episode> listEpisode, String title, String engTitle, String avatarUrl, int releaseYear, String country, float rating, String content, int movieLength) {
        this.id = id;
        this.director = director;
        this.listActor = listActor;
        this.listGenre = listGenre;
        this.listEpisode = listEpisode;
        this.title = title;
        this.engTitle = engTitle;
        this.avatarUrl = avatarUrl;
        this.releaseYear = releaseYear;
        this.country = country;
        this.rating = rating;
        this.content = content;
        this.movieLength = movieLength;
    }

    public static Movie convertPojo(MoviePojo moviePojo){
        Director director = Director.convertPojo(moviePojo.director);
        ArrayList<Genre> listGenre = new ArrayList<>();
        ArrayList<Actor> listActor = new ArrayList<>();
        ArrayList<Episode> listEpisode = new ArrayList<>();
        for(GenrePojo genrePojo : moviePojo.listGenre){
            Genre genre = Genre.convertPojo(genrePojo);
            listGenre.add(genre);
        }
        for(ActorPojo actorPojo : moviePojo.listActor){
            Actor actor = Actor.convertPojo(actorPojo);
            listActor.add(actor);
        }
        for(EpisodePojo episodePojo : moviePojo.listEpisode){
            Episode episode = Episode.convertPojo(episodePojo);
            listEpisode.add(episode);
        }
        moviePojo.avatarUrl = BaseUrl.URL + moviePojo.avatarUrl;
        Movie movie = new Movie(moviePojo.id, director, listActor, listGenre, listEpisode, moviePojo.title,
        moviePojo.engTitle, moviePojo.avatarUrl, moviePojo.releaseYear, moviePojo.country, moviePojo.rating,
        moviePojo.content, moviePojo.movieLength);

        return movie;
    }
}
