package dev.yiray.livedatamvi.repository

import dev.yiray.livedatamvi.retrofit.UserService
import dev.yiray.livedatamvi.retrofit.UserApi
import dev.yiray.livedatamvi.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private val userApi: UserApi?

    interface OnInfoCallBack {
        fun onInformationTaken(user: User?)
    }

    fun getUser(userName: String?, callBack: OnInfoCallBack) {
        userApi!!.findUser(userName).enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                callBack.onInformationTaken(response.body())
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                callBack.onInformationTaken(null)
            }
        })
    }

    companion object {
        private var repository: UserRepository? = null
        val instance: UserRepository?
            get() {
                if (repository == null) {
                    repository = UserRepository()
                }
                return repository
            }
    }

    init {
        userApi = UserService.api
    }
}