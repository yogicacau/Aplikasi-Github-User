package com.makhalibagas.searchgithub.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.makhalibagas.searchgithub.Database.GithubHelper;
import com.makhalibagas.searchgithub.View.Fragment.FragmentFollowers;
import com.makhalibagas.searchgithub.View.Fragment.FragmentFollowing;
import com.makhalibagas.searchgithub.Model.User;
import com.makhalibagas.searchgithub.Model.Useritem;
import com.makhalibagas.searchgithub.R;
import com.makhalibagas.searchgithub.ViewModel.DetailViewModel;

import java.util.Objects;

import static android.provider.BaseColumns._ID;
import static com.makhalibagas.searchgithub.Database.DatabaseContract.GithubColumns.AVATAR;
import static com.makhalibagas.searchgithub.Database.DatabaseContract.GithubColumns.CONTENT_URI;
import static com.makhalibagas.searchgithub.Database.DatabaseContract.GithubColumns.URL;
import static com.makhalibagas.searchgithub.Database.DatabaseContract.GithubColumns.USERNAME;

public class DetailActivity extends AppCompatActivity {

    public static final String DATA_SEARCH = "data";
    private TextView username,company, location,repo,followers,following;
    private ImageView imageView;
    private FloatingActionButton fabFav;
    private boolean isAdded = false;
    private Useritem useritem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getDataDetail();

        ViewPager2 viewPager2 = findViewById(R.id.vp);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager2.setAdapter(new ViewPagerAdapter(this));


        useritem = getIntent().getParcelableExtra(DATA_SEARCH);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(R.string.followers);
                        break;
                    case 1:
                        tab.setText(R.string.following);
                        break;
                    default:
                        break;
                }
            }
        }).attach();



        fabFav = findViewById(R.id.fabFav);
        fabFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdded){
                    remoteFavoriteItem();
                }else {
                    addedFavoriteItem();
                }

                isAdded = !isAdded;
                if (isAdded) fabFav.setImageResource(R.drawable.ic_favorite);
                else fabFav.setImageResource(R.drawable.ic_favorite_border);
            }
        });

        loadGithub();

    }

    public void loadGithub(){
        GithubHelper githubHelper = GithubHelper.getInstance(getApplicationContext());
        githubHelper.open();

        Cursor cursor = getContentResolver().query(Uri.parse(CONTENT_URI + "/" + Objects.requireNonNull(useritem).getId()),null,null,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()) isAdded = true;
            cursor.close();
        }


        if (isAdded) fabFav.setImageResource(R.drawable.ic_favorite);
        else fabFav.setImageResource(R.drawable.ic_favorite_border);
    }


    private void addedFavoriteItem(){
        if (useritem != null){
            ContentValues values = new ContentValues();
            values.put(_ID, useritem.getId());
            values.put(AVATAR, useritem.getAvatarUrl());
            values.put(USERNAME, useritem.getLogin());
            values.put(URL, useritem.getUrl());

            getContentResolver().insert(CONTENT_URI, values);
            Toast.makeText(this, R.string.add, Toast.LENGTH_SHORT).show();
        }
    }

    public void remoteFavoriteItem(){
        getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + useritem.getId()),null,null);

        Toast.makeText(this, R.string.delete, Toast.LENGTH_SHORT).show();
    }
    public static class ViewPagerAdapter extends FragmentStateAdapter{

        ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity){
            super(fragmentActivity);
        }


        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new FragmentFollowers();
                case 1:
                    return new FragmentFollowing();
            }
            return new FragmentFollowers();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }


    private void getDataDetail() {
        Useritem github = getIntent().getParcelableExtra(DATA_SEARCH);
        if (github != null){
            username = findViewById(R.id.username_detail);
            company = findViewById(R.id.company);
            location = findViewById(R.id.lokasi_detail);
            repo = findViewById(R.id.repo);
            followers = findViewById(R.id.followers);
            following = findViewById(R.id.following);
            imageView = findViewById(R.id.img_profile_detail);

            DetailViewModel detailViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DetailViewModel.class);
            detailViewModel.setDataDetail(github.getLogin());
            detailViewModel.getDataDetail().observe(this, new Observer<User>() {
                @Override
                public void onChanged(User useritem) {
                    username.setText(useritem.getName());
                    company.setText(useritem.getCompany());
                    location.setText(useritem.getLocation());
                    repo.setText(String.valueOf(useritem.getPublicRepos()));
                    followers.setText(String.valueOf(useritem.getFollowers()));
                    following.setText(String.valueOf(useritem.getFollowing()));
                    Glide.with(getApplicationContext())
                            .load(useritem.getAvatarUrl())
                            .into(imageView);
                }
            });


            if (getSupportActionBar() != null){
                getSupportActionBar().setTitle("Detail User " + github.getLogin());
            }


        }
    }


}
