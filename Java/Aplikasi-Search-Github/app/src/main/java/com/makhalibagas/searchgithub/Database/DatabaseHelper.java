package com.makhalibagas.searchgithub.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.makhalibagas.searchgithub.Database.DatabaseContract.GithubColumns.AVATAR;
import static com.makhalibagas.searchgithub.Database.DatabaseContract.GithubColumns.TABLE_GITHUB;
import static com.makhalibagas.searchgithub.Database.DatabaseContract.GithubColumns.URL;
import static com.makhalibagas.searchgithub.Database.DatabaseContract.GithubColumns.USERNAME;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "dbname";
    private static final int USER_DB_VERSION = 1;


    DatabaseHelper(Context c){
        super(c,DATABASE_NAME,null,USER_DB_VERSION);
    }


    private static final String SQL_CREATE_TABLE_USER = String.format("CREATE TABLE %s" +
            "(%s INTEGER PRIMARY KEY, " +
            "%s INTEGER, " +
            "%s TEXT NOT NULL," +
            "%s TEXT NOT NULL)", TABLE_GITHUB, _ID,AVATAR, URL, USERNAME);

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GITHUB);
        onCreate(db);
    }


}
