package com.makhalibagas.searchgithub.Retrofit;

import com.makhalibagas.searchgithub.Model.ResponseSearch;
import com.makhalibagas.searchgithub.Model.User;
import com.makhalibagas.searchgithub.Model.Useritem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


    @GET("search/users")
    Call<ResponseSearch> getGithubSearch(
            @Query("q") String username
    );


    @GET("users/{username}")
    Call<User> getUserDetail(@Path("username") String username);


    @GET("users/{username}/followers")
    Call<List<Useritem>> getFollowerData(
            @Header("Authorization") String authorization,
            @Path("username") String username
    );

    @GET("users/{username}/following")
    Call<List<Useritem>> getFollowingData(
            @Header("Authorization") String authorization,
            @Path("username") String username
    );

}
