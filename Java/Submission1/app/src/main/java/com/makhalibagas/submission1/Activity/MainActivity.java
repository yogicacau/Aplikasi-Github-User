package com.makhalibagas.submission1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ListView;

import com.makhalibagas.submission1.Adapter.AdapterUser;
import com.makhalibagas.submission1.Model.User;
import com.makhalibagas.submission1.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> userArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setList();
        setData();
    }

    private void setList() {
        ListView listView = findViewById(R.id.lv);
        AdapterUser adapterUser = new AdapterUser(this, userArrayList);
        listView.setAdapter(adapterUser);
    }
    private void setData(){
        String[] dataName = getResources().getStringArray(R.array.name);
        String[] dataUsername = getResources().getStringArray(R.array.username);
        String[] dataCompany = getResources().getStringArray(R.array.company);
        String[] dataLocation = getResources().getStringArray(R.array.location);
        String[] dataFollowers = getResources().getStringArray(R.array.followers);
        String[] dataFollowing = getResources().getStringArray(R.array.following);
        String[] dataRepository = getResources().getStringArray(R.array.repository);
        @SuppressLint("Recycle") TypedArray dataImage = getResources().obtainTypedArray(R.array.avatar);

        for (int position = 0; position < dataName.length; position++){
            User user = new User();
            user.setName(dataName[position]);
            user.setUsername(dataUsername[position]);
            user.setCompany(dataCompany[position]);
            user.setLocation(dataLocation[position]);
            user.setAvatar(dataImage.getResourceId(position,-1));
            user.setFollowers(dataFollowers[position]);
            user.setFollowing(dataFollowing[position]);
            user.setRepository(dataRepository[position]);
            userArrayList.add(user);
        }
    }

}
