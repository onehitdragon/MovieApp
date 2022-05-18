package com.example.finalapp.model;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoryMovie extends Movie{
    private Calendar previousTime;

    public Calendar getPreviousTime() {
        return previousTime;
    }

    public void setPreviousTime(Calendar previousTime) {
        this.previousTime = previousTime;
    }

    public HistoryMovie(Movie movie, Calendar previousTime){
        super(movie.getId(),
                movie.getDirector(),
                movie.getListActor(),
                movie.getListGenre(),
                movie.getListEpisode(),
                movie.getTitle(),
                movie.getEngTitle(),
                movie.getAvatarUrl(),
                movie.getReleaseYear(),
                movie.getCountry(),
                movie.getRating(),
                movie.getContent(),
                movie.getMovieLength());
        this.previousTime = previousTime;
    }

    public String calcPreviousTime(){
        Calendar currentTime = Calendar.getInstance();
        long agoMillis = currentTime.getTimeInMillis() - previousTime.getTimeInMillis();
        long second = agoMillis / 1000;
        if(second / 60 == 0){
            return " vừa xem";
        }
        long minus = second / 60;
        if(minus / 60 == 0){
            return minus + " phút trước";
        }
        long hour = minus / 60;
        if(hour / 24 == 0){
            return hour + " giờ trước";
        }
        long day = hour / 24;
        return day + " ngày trước";
    }
}
