package dev.yiray.livedatamvi.ui.search

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import dev.yiray.livedatamvi.repository.UserRepository.OnInfoCallBack
import dev.yiray.livedatamvi.repository.UserRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import dev.yiray.livedatamvi.model.User

class SearchViewModel : ViewModel(), OnInfoCallBack {
    private val repository: UserRepository? = UserRepository.instance
    private val userMutableLiveData: MutableLiveData<User?> = MutableLiveData()
    val user: LiveData<User?>
        get() {
            val defaultName = "iray68"
            findUser(defaultName)
            return userMutableLiveData
        }

    fun findUser(accountName: String?) {
        if (TextUtils.isEmpty(accountName)) {
            return
        }
        repository!!.getUser(accountName, this)
    }

    override fun onInformationTaken(user: User?) {
        userMutableLiveData.postValue(user)
    }

}