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
        ContentValues contentValues = new ContentValues();
        contentValues.put("HistoryMovie", gson.toJson(historyMovie));
        if(!delete(historyMovie)){
            if(get().size() >= 5){
                deleteMostLater();
            }
        }

        // insert
        SQLiteDatabase DB = mySqliteOpenHelper.getWritableDatabase();
        DB.insert("HistoryMovie", null, contentValues);
    }

    public boolean delete(HistoryMovie _historyMovie){
        SQLiteDatabase DB = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM HistoryMovie", null);
        while(cursor.moveToNext()){
            String json = cursor.getString(1);
            HistoryMovie historyMovie = gson.fromJson(json, HistoryMovie.class);
            if(historyMovie.getId() == _historyMovie.getId()){
                DB = mySqliteOpenHelper.getWritableDatabase();
                DB.execSQL("DELETE FROM HistoryMovie WHERE Id = " + cursor.getInt(0));
                return true;
            }
        }
        cursor.close();
        return false;
    }

    private void deleteMostLater(){
        SQLiteDatabase DB = mySqliteOpenHelper.getWritableDatabase();
        DB.execSQL("DELETE FROM HistoryMovie WHERE Id = "
                + "(SELECT MIN(Id) FROM HistoryMovie)");
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
