package com.example.finalapp.localrepository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalapp.model.Episode;
import com.example.finalapp.model.InfoDownloadMovie;
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

    public boolean insert(InfoDownloadMovie infoDownloadMovie){
        SQLiteDatabase DB = mySqliteOpenHelper.getWritableDatabase();
        if(infoDownloadMovieIsExist(infoDownloadMovie)) return false;
        ContentValues contentValues = new ContentValues();
        contentValues.put("InfoDownloadMovie", gson.toJson(infoDownloadMovie));
        DB.insert("DownloadMovie", null, contentValues);

        return true;
    }

    private boolean infoDownloadMovieIsExist(InfoDownloadMovie _infoDownloadMovie){
        SQLiteDatabase DB = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM DownloadMovie", null);
        while(cursor.moveToNext()){
            String json = cursor.getString(1);
            InfoDownloadMovie infoDownloadMovie = gson.fromJson(json, InfoDownloadMovie.class);
            if(infoDownloadMovie.getMovie().getId() == _infoDownloadMovie.getMovie().getId() && infoDownloadMovie.getEpisode().getNumber() == _infoDownloadMovie.getEpisode().getNumber()){
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    public ArrayList<InfoDownloadMovie> get(){
        ArrayList<InfoDownloadMovie> listDownloadMovie = new ArrayList<>();
        SQLiteDatabase DB = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM DownloadMovie ORDER BY Id DESC", null);
        while(cursor.moveToNext()){
            String json = cursor.getString(1);
            InfoDownloadMovie infoDownloadMovie = gson.fromJson(json, InfoDownloadMovie.class);
            listDownloadMovie.add(infoDownloadMovie);
        }
        cursor.close();

        return listDownloadMovie;
    }

    public void delete(InfoDownloadMovie _infoDownloadMovie){
        SQLiteDatabase DB = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM DownloadMovie", null);
        while (cursor.moveToNext()){
            String json = cursor.getString(1);
            InfoDownloadMovie infoDownloadMovie = gson.fromJson(json, InfoDownloadMovie.class);
            if(infoDownloadMovie.getMovie().getId() == _infoDownloadMovie.getMovie().getId() && infoDownloadMovie.getEpisode().getNumber() == _infoDownloadMovie.getEpisode().getNumber()){
                DB = mySqliteOpenHelper.getWritableDatabase();
                DB.execSQL("DELETE FROM DownloadMovie WHERE Id = " + cursor.getInt(0));
                break;
            }
        }
        cursor.close();
    }
}
