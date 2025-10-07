package com.example.myapplication.api

import com.example.myapplication.data.SolvedData
import com.example.myapplication.data.SubmissionResponse
import com.example.myapplication.data.UserBadgesData
import com.example.myapplication.data.UserProfileData
import retrofit2.http.GET
import retrofit2.http.Path

interface LeetcodeApi {
    @GET("{username}")
    suspend fun getUserProfile(@Path("username") username: String): UserProfileData
    @GET("{username}/submission?limit=30")
    suspend fun getUserSubmissions(@Path("username") username: String): SubmissionResponse
    @GET("{username}/solved")
    suspend fun getUserSolvedStats(@Path("username") username: String): SolvedData
    @GET("{username}/badges")
    suspend fun getUserBadges(@Path("username") username: String): UserBadgesData
}