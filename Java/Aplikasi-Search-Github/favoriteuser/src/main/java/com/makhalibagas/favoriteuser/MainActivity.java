package com.makhalibagas.favoriteuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.makhalibagas.favoriteuser.Adapter.FavoriteAdapter;
import com.makhalibagas.favoriteuser.Database.GithubHelper;

import static com.makhalibagas.favoriteuser.Database.DatabaseContract.GithubColumns.CONTENT_URI;

public class MainActivity extends AppCompatActivity {
    private Cursor listGithub;
    private FavoriteAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GithubHelper githubHelper = GithubHelper.getInstance(getApplicationContext());
        githubHelper.open();

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
    private class LoadFavoriteItem extends AsyncTask<Void, Void, Cursor> {
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
