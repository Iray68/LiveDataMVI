package dev.yiray.livedatamvi.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserService {
    private const val BASE_URL = "https://api.github.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api: UserApi
        get() = retrofit.create(UserApi::class.java)
}