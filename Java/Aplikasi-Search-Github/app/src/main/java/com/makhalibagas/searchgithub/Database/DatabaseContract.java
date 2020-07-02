package com.makhalibagas.searchgithub.Database;


import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.makhalibagas.searchgithub";
    public static final String SCHEME = "content";

    public static final class GithubColumns implements BaseColumns {


        public static final String TABLE_GITHUB = "table_github";
        public static final String AVATAR = "avatar";
        public static final String URL = "url";
        public static final String USERNAME = "username";


        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_GITHUB)
                .build();

    }

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String  columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }


}
