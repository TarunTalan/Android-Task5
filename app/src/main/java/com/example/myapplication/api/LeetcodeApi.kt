package com.example.myapplication.api

import com.example.myapplication.Submission
import com.example.myapplication.SubmissionResponse
import com.example.myapplication.UserProfileData
import retrofit2.http.GET
import retrofit2.http.Path

interface LeetcodeApi {
    @GET("{username}")
    suspend fun getUserProfile(@Path("username") username: String): UserProfileData
    @GET("{username}/submission?limit=30")
    suspend fun getUserSubmissions(@Path("username") username: String): SubmissionResponse
}