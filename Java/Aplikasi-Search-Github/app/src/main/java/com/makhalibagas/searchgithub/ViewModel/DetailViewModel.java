package com.makhalibagas.searchgithub.ViewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.makhalibagas.searchgithub.Model.User;
import com.makhalibagas.searchgithub.Retrofit.ApiService;
import com.makhalibagas.searchgithub.Retrofit.RetroConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<User> useritem;

    public void setDataDetail(String username){

        ApiService apiService = RetroConfiguration.getRetrofit().create(ApiService.class);
        Call<User> call = apiService.getUserDetail(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                useritem.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public LiveData<User> getDataDetail(){
       if (useritem == null){
           useritem = new MutableLiveData<>();
       }
       return useritem;
    }
}
