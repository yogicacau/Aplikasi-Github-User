package com.makhalibagas.searchgithub.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;

import static com.makhalibagas.searchgithub.Database.DatabaseContract.GithubColumns.TABLE_GITHUB;

public class GithubHelper {


    private static final String DATABASE_NAME = TABLE_GITHUB;
    private static DatabaseHelper databaseHelper;
    private static GithubHelper githubHelper;
    private static SQLiteDatabase database;



    public GithubHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public static GithubHelper getInstance(Context context){
        if (githubHelper == null){
            synchronized (SQLiteOpenHelper.class){
                if (githubHelper == null){
                    githubHelper = new GithubHelper(context);
                }
            }
        }
        return githubHelper;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }


    public Cursor queryById(String id) {
        return database.query(DATABASE_NAME, null
                , MediaStore.Audio.Playlists.Members._ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryAll() {
        return database.query(DATABASE_NAME
                , null
                , null
                , null
                , null
                , null
                , MediaStore.Audio.Playlists.Members._ID + " ASC");
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_NAME, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_NAME, values, MediaStore.Audio.Playlists.Members._ID + " =?", new String[]{id});
    }

    public int delete(String id) {
        return database.delete(DATABASE_NAME, MediaStore.Audio.Playlists.Members._ID + " = ?", new String[]{id});
    }
}
