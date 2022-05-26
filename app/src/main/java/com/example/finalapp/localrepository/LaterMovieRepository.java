package com.example.finalapp.localrepository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.finalapp.model.Movie;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LaterMovieRepository {
    private MySqliteOpenHelper mySqliteOpenHelper;
    private Gson gson;

    public LaterMovieRepository(Context context){
        mySqliteOpenHelper = MySqliteOpenHelper.getInstance(context);
        gson = new Gson();
    }

    public void insert(Movie movie){
        SQLiteDatabase DB = mySqliteOpenHelper.getWritableDatabase();
        delete(movie);
        ContentValues contentValues = new ContentValues();
        contentValues.put("LaterMovie", gson.toJson(movie));
        DB.insert("LaterMovie", null, contentValues);
    }

    public void delete(Movie movie){
        SQLiteDatabase DB = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM LaterMovie", null);
        while(cursor.moveToNext()){
            String json = cursor.getString(1);
            Movie laterMovie = gson.fromJson(json, Movie.class);
            if(laterMovie.getId() == movie.getId()){
                DB = mySqliteOpenHelper.getWritableDatabase();
                DB.execSQL("DELETE FROM LaterMovie WHERE Id = " + cursor.getInt(0));
                break;
            }
        }
        cursor.close();
    }

    public ArrayList<Movie> get(){
        ArrayList<Movie> listHistoryMovie = new ArrayList<>();
        SQLiteDatabase DB = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM LaterMovie ORDER BY Id DESC", null);
        while(cursor.moveToNext()){
            String json = cursor.getString(1);
            Movie movie = gson.fromJson(json, Movie.class);
            listHistoryMovie.add(movie);
        }
        cursor.close();

        return listHistoryMovie;
    }
}
