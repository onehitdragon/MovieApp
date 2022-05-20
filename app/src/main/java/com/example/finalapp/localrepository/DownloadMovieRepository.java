package com.example.finalapp.localrepository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalapp.model.Episode;
import com.example.finalapp.model.Movie;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DownloadMovieRepository {
    private MySqliteOpenHelper mySqliteOpenHelper;
    private Gson gson;

    public DownloadMovieRepository(Context context){
        mySqliteOpenHelper = MySqliteOpenHelper.getInstance(context);
        gson = new Gson();
    }

    public boolean insert(Movie movie, Episode episode){
        SQLiteDatabase DB = mySqliteOpenHelper.getWritableDatabase();
        if(movieAndEpisodeIsExist(movie, episode)) return false;
        ContentValues contentValues = new ContentValues();
        contentValues.put("DownloadMovie", gson.toJson(movie));
        contentValues.put("Episode", gson.toJson(episode));
        DB.insert("DownloadMovie", null, contentValues);

        return true;
    }

    private boolean movieAndEpisodeIsExist(Movie movie, Episode episode){
        SQLiteDatabase DB = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM DownloadMovie", null);
        while(cursor.moveToNext()){
            String json = cursor.getString(1);
            Movie downloadMovie = gson.fromJson(json, Movie.class);
            json = cursor.getString(2);
            Episode episodeMovie = gson.fromJson(json, Episode.class);
            if(downloadMovie.getId() == movie.getId() && episodeMovie.getNumber() == episode.getNumber()){
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    public ArrayList<Movie> get(){
        ArrayList<Movie> listHistoryMovie = new ArrayList<>();
        SQLiteDatabase DB = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM DownloadMovie ORDER BY Id DESC", null);
        while(cursor.moveToNext()){
            String json = cursor.getString(1);
            Movie movie = gson.fromJson(json, Movie.class);
            listHistoryMovie.add(movie);
        }
        cursor.close();

        return listHistoryMovie;
    }
}
