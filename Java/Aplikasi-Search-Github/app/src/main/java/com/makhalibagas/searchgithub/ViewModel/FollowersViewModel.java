package com.makhalibagas.searchgithub.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.makhalibagas.searchgithub.Model.Useritem;
import com.makhalibagas.searchgithub.Retrofit.ApiService;
import com.makhalibagas.searchgithub.Retrofit.RetroConfiguration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersViewModel extends ViewModel {


    public static final String API_KEY = "15b0965e73a0f1dc80d5ed271849a0b61f9bd184";
    private MutableLiveData<List<Useritem>> userMutableLiveData ;


    public void setDataFollowers(String username){
        ApiService apiService = RetroConfiguration.getRetrofit().create(ApiService.class);
        Call<List<Useritem>> call = apiService.getFollowerData(API_KEY, username);
        call.enqueue(new Callback<List<Useritem>>() {
            @Override
            public void onResponse(Call<List<Useritem>> call, Response<List<Useritem>> response) {
                userMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Useritem>> call, Throwable t) {

            }
        });
    }

    public LiveData<List<Useritem>> getDataFollowers(){
        if (userMutableLiveData == null){
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }
}
