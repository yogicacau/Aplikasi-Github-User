package com.makhalibagas.submission2.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.tabs.TabLayout;
import com.makhalibagas.submission2.Adapter.ViewPagerAdapter;
import com.makhalibagas.submission2.Connection.ApiService;
import com.makhalibagas.submission2.Connection.RetrofitConfiguration;
import com.makhalibagas.submission2.Model.UserGithub;
import com.makhalibagas.submission2.Model.UserGithubDetail;
import com.makhalibagas.submission2.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private TextView repo,follower,following,username,bio,email;
    private ImageView avatar;
    private UserGithub userGithub;
    private MaterialFavoriteButton materialFavoriteButton;
    private ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this,getSupportFragmentManager());
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.vp);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setElevation(0);
        setDataDetailUser();
        setMaterialFavoriteButton();


        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Detail " + userGithub.getLogin());
        }
    }

    private void setMaterialFavoriteButton(){
        materialFavoriteButton = findViewById(R.id.mfb_favorite);
        materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {

            }
        });
    }
    private void setDataDetailUser(){
        userGithub = getIntent().getParcelableExtra("DATA_USER");
        if (userGithub != null){
            repo = findViewById(R.id.tv_repo);
            follower = findViewById(R.id.tv_followers);
            following = findViewById(R.id.tv_following);
            username = findViewById(R.id.tv_username);
            bio = findViewById(R.id.tv_bio);
            email = findViewById(R.id.tv_email);
            avatar = findViewById(R.id.image_user_detail);

            final LinearLayout linearLayout = findViewById(R.id.linear_layout);
            ApiService apiService = RetrofitConfiguration.getRetrofit().create(ApiService.class);
            Call<UserGithubDetail> call = apiService.getUserDetail(userGithub.getLogin());
            call.enqueue(new Callback<UserGithubDetail>() {
                @Override
                public void onResponse(Call<UserGithubDetail> call, Response<UserGithubDetail> response) {
                    if (response.body() != null){
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        repo.setText(String.valueOf(response.body().getPublicRepos()));
                        follower.setText(String.valueOf(response.body().getFollowers()));
                        following.setText(String.valueOf(response.body().getFollowing()));
                        username.setText(response.body().getName());
                        bio.setText(response.body().getBio());
                        email.setText(response.body().getEmail());
                        Glide.with(getApplicationContext())
                                .load(response.body().getAvatarUrl())
                                .into(avatar);
                        username.setVisibility(View.VISIBLE);
                        bio.setVisibility(View.VISIBLE);
                        email.setVisibility(View.VISIBLE);
                        avatar.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<UserGithubDetail> call, Throwable t) {

                }
            });
        }
    }
}
