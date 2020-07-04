package com.makhalibagas.githubuser.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Bagas Makhali on 7/2/2020.
 */
class RetroConfig {

    companion object Factory{
        fun getRetrofit() : ApiService{

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)

        }
    }
}