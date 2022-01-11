package dev.yiray.livedatamvi.model

import com.google.gson.annotations.SerializedName

data class User(
    val login: String,
    val id: Int,
    val name: String,
    val company: String,
    val blog: String,
    val location: String,
    val email: String,
    val followers:Int,
    val following: Int,
    @SerializedName("html_url") val url: String)