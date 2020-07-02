package com.makhalibagas.searchgithub.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.makhalibagas.searchgithub.Adapter.SearchAdapter;
import com.makhalibagas.searchgithub.Model.Useritem;
import com.makhalibagas.searchgithub.R;
import com.makhalibagas.searchgithub.ViewModel.SearchViewModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SearchAdapter searchAdapter;
    private RecyclerView recyclerView;
    private SearchViewModel searchViewModel;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        setSearchView();
        searchViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);
        searchViewModel.getDataSearch().observe(this, new Observer<List<Useritem>>() {
            @Override
            public void onChanged(List<Useritem> useritems) {
                searchAdapter = new SearchAdapter(getApplicationContext(),useritems);
                recyclerView.setAdapter(searchAdapter);
                showLoading(false);
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
    private void setSearchView(){
        SearchView searchView = findViewById(R.id.sv);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewModel.setDataSearch(query);
                showLoading(true);
                progressBar.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.settings) {
            intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
    }
}
