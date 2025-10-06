package com.example.myapplication

import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LeetcodeApi {
    @GET("{username}")
    suspend fun getUserProfile(@Path("username") username: String): UserProfileData
}

