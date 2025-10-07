package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class UserProfileData(
    @SerializedName("about")
    val about: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("birthday")
    val birthday: String,
    val company: Any,
    @SerializedName("country")
    val country: String,
    @SerializedName("gitHub")
    val gitHub: String,
    val linkedIN: Any,
    @SerializedName("name")
    val name: String,
    @SerializedName("ranking")
    val ranking: Int,
    @SerializedName("reputation")
    val reputation: Int,
    val school: String,
    val skillTags: List<Any>,
    val twitter: Any,
    @SerializedName("username")
    val username: String,
    val website: List<Any>
)