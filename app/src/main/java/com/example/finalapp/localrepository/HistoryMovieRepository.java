package com.example.finalapp.localrepository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.finalapp.model.HistoryMovie;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HistoryMovieRepository {
    private MySqliteOpenHelper mySqliteOpenHelper;
    private Gson gson;

    public HistoryMovieRepository(Context context) {
        this.mySqliteOpenHelper = MySqliteOpenHelper.getInstance(context);
        this.gson = new Gson();
    }

    public void insert(HistoryMovie historyMovie){
        SQLiteDatabase DB = mySqliteOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("HistoryMovie", gson.toJson(historyMovie));
        delete(historyMovie);
        DB.insert("HistoryMovie", null, contentValues);
    }

    public void delete(HistoryMovie _historyMovie){
        SQLiteDatabase DB = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM HistoryMovie", null);
        while(cursor.moveToNext()){
            String json = cursor.getString(1);
            HistoryMovie historyMovie = gson.fromJson(json, HistoryMovie.class);
            if(historyMovie.getId() == _historyMovie.getId()){
                DB = mySqliteOpenHelper.getWritableDatabase();
                DB.execSQL("DELETE FROM HistoryMovie WHERE Id = " + cursor.getInt(0));
                break;
            }
        }
        cursor.close();
    }

    public ArrayList<HistoryMovie> get(){
        ArrayList<HistoryMovie> listHistoryMovie = new ArrayList<>();
        SQLiteDatabase DB = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM HistoryMovie ORDER BY Id DESC", null);
        while(cursor.moveToNext()){
            String json = cursor.getString(1);
            HistoryMovie historyMovie = gson.fromJson(json, HistoryMovie.class);
            listHistoryMovie.add(historyMovie);
        }
        cursor.close();

        return listHistoryMovie;
    }
}
