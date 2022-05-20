package com.example.finalapp.localrepository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MySqliteOpenHelper extends SQLiteOpenHelper {
    private static MySqliteOpenHelper instance;
    public static MySqliteOpenHelper getInstance(Context context){
        if(instance == null){
            instance = new MySqliteOpenHelper(context);
        }
        return instance;
    }
    private MySqliteOpenHelper(@Nullable Context context) {
        super(context, "AppMovie.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        Log.e("TAG", "onCreateDB: ");
        String query = "CREATE TABLE IF NOT EXISTS HistoryMovie("
                + "Id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "HistoryMovie TEXT"
                + ")";
        DB.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS LaterMovie("
                + "Id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "LaterMovie TEXT"
                + ")";
        DB.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS DownloadMovie("
                + "Id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "DownloadMovie TEXT,"
                + "Episode TEXT"
                + ")";
        DB.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        Log.e("TAG", "onUpgradeDB: ");
        String query = "DROP TABLE IF EXISTS HistoryMovie";
        DB.execSQL(query);
    }
}
