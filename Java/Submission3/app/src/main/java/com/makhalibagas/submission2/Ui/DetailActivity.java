package com.makhalibagas.submission2.Ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.tabs.TabLayout;
import com.makhalibagas.submission2.Adapter.ViewPagerAdapter;
import com.makhalibagas.submission2.Connection.ApiService;
import com.makhalibagas.submission2.Connection.RetrofitConfiguration;
import com.makhalibagas.submission2.Database.UserDbContract;
import com.makhalibagas.submission2.Database.UserDbHelper;
import com.makhalibagas.submission2.Database.UserHelper;
import com.makhalibagas.submission2.Model.UserGithub;
import com.makhalibagas.submission2.Model.UserGithubDetail;
import com.makhalibagas.submission2.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makhalibagas.submission2.Database.UserDbContract.UserColumns.TABLE_USER_NAME;

public class DetailActivity extends AppCompatActivity {

    private TextView repo,follower,following,username,bio,email,repo1,followers1,following1;
    private ImageView avatar;
    private UserGithub userGithub;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private UserHelper userHelper;
    private ArrayList<UserGithub> userGithubList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        setDataDetailUser();
        setOnClickFavoriteButton();
        userHelper = UserHelper.getInstance(getApplicationContext());
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Detail " + userGithub.getLogin());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }


    }

    private void setOnClickFavoriteButton(){
        MaterialFavoriteButton materialFavoriteButton = findViewById(R.id.mfb_favorite);
        if (EXIST(userGithub.getLogin())){
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite){
                        userGithubList = userHelper.getDataUser();
                        userHelper.userInsert(userGithub);
                        Toast.makeText(DetailActivity.this, "Success added favorite", Toast.LENGTH_SHORT).show();
                    }else {
                        userGithubList = userHelper.getDataUser();
                        userHelper.userDelete(String.valueOf(userGithub.getId()));
                        Toast.makeText(DetailActivity.this, "Success delete favorite", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite){
                        userGithubList = userHelper.getDataUser();
                        userHelper.userInsert(userGithub);
                        Toast.makeText(DetailActivity.this, "Success added favorite", Toast.LENGTH_SHORT).show();
                    }else {
                        userGithubList = userHelper.getDataUser();
                        userHelper.userDelete(String.valueOf(userGithub.getId()));
                        Toast.makeText(DetailActivity.this, "Success delete favorite", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private boolean EXIST(String username){
        String change = UserDbContract.UserColumns.USERNAME + "=?";
        String[] changeArg = {username};
        String limit = "1";
        userHelper = new UserHelper(this);
        userHelper.open();
        userGithub = getIntent().getParcelableExtra("DATA_USER");

        UserDbHelper userDbHelper = new UserDbHelper(getApplicationContext());
        SQLiteDatabase database = userDbHelper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.query(TABLE_USER_NAME,null,change,changeArg,null,null,null,limit);
        boolean exist = (cursor.getCount() > 0 );
        cursor.close();
        return exist;
    }
    private void setDataDetailUser(){
        userGithub = getIntent().getParcelableExtra("DATA_USER");
        if (userGithub != null){
            repo = findViewById(R.id.tv_repo);
            follower = findViewById(R.id.tv_followers);
            following = findViewById(R.id.tv_following);
            repo1 = findViewById(R.id.repo);
            followers1 = findViewById(R.id.followers);
            following1 = findViewById(R.id.following);
            username = findViewById(R.id.tv_username);
            bio = findViewById(R.id.tv_bio);
            email = findViewById(R.id.tv_email);
            avatar = findViewById(R.id.image_user_detail);

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
                        username.setText(response.body().getName());
                        bio.setText(response.body().getBio());
                        email.setText(response.body().getEmail());
                        Glide.with(getApplicationContext())
                                .load(response.body().getAvatarUrl())
                                .into(avatar);
                        repo.setVisibility(View.VISIBLE);
                        follower.setVisibility(View.VISIBLE);
                        following.setVisibility(View.VISIBLE);
                        repo1.setVisibility(View.VISIBLE);
                        followers1.setVisibility(View.VISIBLE);
                        following1.setVisibility(View.VISIBLE);
                        username.setVisibility(View.VISIBLE);
                        bio.setVisibility(View.VISIBLE);
                        email.setVisibility(View.VISIBLE);
                        avatar.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<UserGithubDetail> call, Throwable t) {
                    Toast.makeText(DetailActivity.this, "not load data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void init() {
        adapter = new ViewPagerAdapter(this,getSupportFragmentManager());
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.vp);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        tabLayout.setElevation(0);

    }
}
