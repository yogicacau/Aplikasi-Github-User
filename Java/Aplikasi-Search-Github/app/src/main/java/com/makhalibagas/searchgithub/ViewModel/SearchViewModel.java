package com.makhalibagas.searchgithub.ViewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.makhalibagas.searchgithub.Model.ResponseSearch;
import com.makhalibagas.searchgithub.Model.Useritem;
import com.makhalibagas.searchgithub.Retrofit.ApiService;
import com.makhalibagas.searchgithub.Retrofit.RetroConfiguration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {


    private MutableLiveData<List<Useritem>> useritem = new MutableLiveData<>();

    public void setDataSearch(String query){
        try {

            ApiService apiService = RetroConfiguration.getRetrofit().create(ApiService.class);
            Call<ResponseSearch> searchCall = apiService.getGithubSearch(query);
            searchCall.enqueue(new Callback<ResponseSearch>() {
                @Override
                public void onResponse(Call<ResponseSearch> call, Response<ResponseSearch> response) {
                    if (response.body() != null){
                        List<Useritem> useritems = response.body().getItems();
                        useritem.postValue(useritems);
                    }
                }

                @Override
                public void onFailure(Call<ResponseSearch> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public LiveData<List<Useritem>> getDataSearch(){
        return useritem;
    }
}
