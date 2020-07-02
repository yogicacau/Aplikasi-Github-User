package com.makhalibagas.submission2.Connection;

import com.makhalibagas.submission2.Model.UserGithub;
import com.makhalibagas.submission2.Model.UserGithubDetail;
import com.makhalibagas.submission2.Model.ResponseSearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
//    https://api.github.com/search/users?q={username}
//    https://api.github.com/users/{username}
//    https://api.github.com/users/{username}/followers
//    https://api.github.com/users/{username}/following
//


    @GET("/users")
    Call<List<UserGithub>> getGithubUser(@Header("Authorization") String token);


    @GET("/users/{username}/followers")
    Call<List<UserGithub>> getUserFollowers(@Path("username") String username);

    @GET("/users/{username}/following")
    Call<List<UserGithub>> getUserFollowing(@Path("username") String username);


    @GET("/search/users")
    Call<ResponseSearch> getGithubSearch(
            @Query("q") String username
    );

    @GET("users/{username}")
    Call<UserGithubDetail> getUserDetail(@Path("username") String username);

}
