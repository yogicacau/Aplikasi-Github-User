package com.makhalibagas.favoriteuser.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.makhalibagas.favoriteuser.Database.GithubHelper;

import java.util.Objects;

import static com.makhalibagas.favoriteuser.Database.DatabaseContract.AUTHORITY;
import static com.makhalibagas.favoriteuser.Database.DatabaseContract.GithubColumns.CONTENT_URI;
import static com.makhalibagas.favoriteuser.Database.DatabaseContract.GithubColumns.TABLE_GITHUB;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {
    }


    private static final int GITHUB = 0;
    private static final int GITHUB_ID = 1;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {
        uriMatcher.addURI(AUTHORITY, TABLE_GITHUB, GITHUB);

        uriMatcher.addURI(AUTHORITY, TABLE_GITHUB + "/#",GITHUB_ID);
    }


    private GithubHelper githubHelper;


    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        githubHelper = new GithubHelper(getContext());
        githubHelper.open();
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor;
        switch (uriMatcher.match(uri)){
            case GITHUB:
                cursor = githubHelper.queryAll();
                break;
            case GITHUB_ID:
                cursor = githubHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        Objects.requireNonNull(cursor).setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long added;
        Uri contentUri = null;

        if (uriMatcher.match(uri) == GITHUB) {
            added = githubHelper.insert(values);
            if (added > 0) {
                contentUri = ContentUris.withAppendedId(CONTENT_URI, added);
            }
        } else {
            added = 0;
        }


        if (added > 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return contentUri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int delete;

        if (uriMatcher.match(uri) == GITHUB_ID) {
            delete = githubHelper.delete(uri.getLastPathSegment());
        } else {
            delete = 0;
        }

        if (delete > 0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }

        return delete;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int update;
        if (uriMatcher.match(uri) == GITHUB_ID) {
            update = githubHelper.update(uri.getLastPathSegment(), values);
        } else {
            update = 0;
        }

        if (update > 0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }

        return update;
    }
}
