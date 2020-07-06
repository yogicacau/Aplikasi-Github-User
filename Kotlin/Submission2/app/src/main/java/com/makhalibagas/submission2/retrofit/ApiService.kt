package com.makhalibagas.submission2.retrofit
import com.makhalibagas.submission2.model.detailUser.DetailUser
import com.makhalibagas.submission2.model.repository.Repository
import com.makhalibagas.submission2.model.user.ResponseUser
import com.makhalibagas.submission2.model.user.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Bagas Makhali on 7/2/2020.
 */
interface ApiService {

    @GET("search/users")
    @Headers("Authorization:b082949e2b8f06ec054563d89049516278fa321c")
    fun getSearchData(
        @Query("q") query: String
    ) : Call<ResponseUser>


    @GET("users/{username}")
    @Headers("Authorization:b082949e2b8f06ec054563d89049516278fa321c")
    fun getDetailUser(
        @Path("username") username : String
    ) : Call<DetailUser>


    @GET("users/{username}/followers")
    @Headers("Authorization:b082949e2b8f06ec054563d89049516278fa321c")
    fun getFollowersData(
        @Path("username") username : String,
        @Query("page") page : String
    ) : Call<List<User>>


    @GET("users/{username}/following")
    @Headers("Authorization:b082949e2b8f06ec054563d89049516278fa321c")
    fun getFollowingData(
        @Path("username") username : String,
        @Query("page") page : String
    ) : Call<List<User>>

    //https://api.github.com/users/sidiqpermana/followers?page=5
    @GET("users/{username}/repos")
    @Headers("Authorization:b082949e2b8f06ec054563d89049516278fa321c")
    fun getRepositoryData(
        @Path("username") username : String
    ) : Call<List<Repository>>

}