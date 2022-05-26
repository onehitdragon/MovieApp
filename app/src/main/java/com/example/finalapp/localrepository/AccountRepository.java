package com.example.finalapp.localrepository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalapp.model.Account;
import com.google.gson.Gson;

public class AccountRepository {
    private MySqliteOpenHelper mySqliteOpenHelper;
    private Gson gson;

    public AccountRepository(Context context){
        mySqliteOpenHelper = MySqliteOpenHelper.getInstance(context);
        gson = new Gson();
    }

    public void insert(Account account){
        SQLiteDatabase DB = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Account", null);
        if(cursor.moveToNext()){
            cursor.close();
            return;
        }
        DB = mySqliteOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Account", gson.toJson(account));
        DB.insert("Account", null, contentValues);
    }

    public void delete(){
        SQLiteDatabase DB = mySqliteOpenHelper.getWritableDatabase();
        DB.execSQL("DELETE FROM Account");
    }

    public Account get(){
        SQLiteDatabase DB = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Account", null);
        if(cursor.moveToNext()){
            Account account = gson.fromJson(cursor.getString(0), Account.class);
            cursor.close();
            return account;
        }
        return null;
    }
}
