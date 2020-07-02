package com.makhalibagas.submission2.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.makhalibagas.submission2.Adapter.AdapterSearch;
import com.makhalibagas.submission2.Adapter.AdapterUser;
import com.makhalibagas.submission2.Connection.ApiService;
import com.makhalibagas.submission2.Connection.RetrofitConfiguration;
import com.makhalibagas.submission2.Model.ResponseSearch;
import com.makhalibagas.submission2.Model.UserGithub;
import com.makhalibagas.submission2.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ShimmerFrameLayout shimmerFrameLayout;
    private SearchView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();
        getDataUser();
        init();


    }

    private void init() {
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        ImageView img = findViewById(R.id.img_settings);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }
        });
        ImageView imgFav = findViewById(R.id.img_favorite);
        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
            }
        });
    }
    private void initViews() {
        rv = findViewById(R.id.rv_user);
        sv = findViewById(R.id.sv_user);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        rv.setLayoutManager(new LinearLayoutManager(this));
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchUser(newText);
                return false;
            }
        });
    }
    private void getDataUser(){
        ApiService apiService = RetrofitConfiguration.getRetrofit().create(ApiService.class);
        Call<List<UserGithub>> call = apiService.getGithubUser("10d995868a5bfc2e27f807dd73d5a65ffeb5b0b4");
        call.enqueue(new Callback<List<UserGithub>>() {
            @Override
            public void onResponse(Call<List<UserGithub>> call, Response<List<UserGithub>> response) {

                if (response.body() != null){
                    AdapterUser adapterUser = new AdapterUser(getApplicationContext(),response.body());
                    rv.setAdapter(adapterUser);
                    shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    shimmerFrameLayout.stopShimmer();
                }

            }

            @Override
            public void onFailure(Call<List<UserGithub>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "not load data", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void getSearchUser(String username){
        ApiService apiService = RetrofitConfiguration.getRetrofit().create(ApiService.class);
        Call<ResponseSearch> call = apiService.getGithubSearch(username);
        call.enqueue(new Callback<ResponseSearch>() {
            @Override
            public void onResponse(Call<ResponseSearch> call, Response<ResponseSearch> response) {

                if (response.body() != null){
                    AdapterSearch adapterSearch = new AdapterSearch(response.body().getItems());
                    rv.setAdapter(adapterSearch);
                }
            }

            @Override
            public void onFailure(Call<ResponseSearch> call, Throwable t) {
                Toast.makeText(MainActivity.this, "not load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
