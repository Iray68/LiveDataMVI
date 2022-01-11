package dev.yiray.livedatamvi.retrofit

import retrofit2.http.GET

import dev.yiray.livedatamvi.model.User
import retrofit2.Call
import retrofit2.http.Path

interface UserApi {
    @GET("users/{user}")
    fun findUser(@Path("user") user: String?): Call<User?>
}