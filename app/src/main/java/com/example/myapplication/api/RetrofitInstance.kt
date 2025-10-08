package com.example.myapplication.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://alfa-leetcode-api-88t9.onrender.com/"
    val apiService: LeetcodeApi by lazy {
            Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LeetcodeApi::class.java)
    }
}