package com.makhalibagas.searchgithub.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.makhalibagas.searchgithub.Adapter.FavoriteAdapter;
import com.makhalibagas.searchgithub.Database.GithubHelper;
import com.makhalibagas.searchgithub.R;

import java.util.Objects;

import static com.makhalibagas.searchgithub.Database.DatabaseContract.GithubColumns.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity {

    private Cursor listGithub;
    private FavoriteAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        GithubHelper githubHelper = GithubHelper.getInstance(getApplicationContext());
        githubHelper.open();


        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.favorite);
        RecyclerView recyclerView = findViewById(R.id.rvFav);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoriteAdapter = new FavoriteAdapter(getApplicationContext());
        favoriteAdapter.setFavoriteList(listGithub);
        recyclerView.setAdapter(favoriteAdapter);
        if (savedInstanceState == null){
            new LoadFavoriteItem().execute();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class LoadFavoriteItem extends AsyncTask<Void, Void, Cursor>{
        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI, null,null,null,null);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            listGithub = cursor;
            favoriteAdapter.setFavoriteList(listGithub);
            favoriteAdapter.notifyDataSetChanged();
        }
    }
}
