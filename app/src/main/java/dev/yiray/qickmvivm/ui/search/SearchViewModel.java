package dev.yiray.qickmvivm.ui.search;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dev.yiray.qickmvivm.model.User;
import dev.yiray.qickmvivm.repository.UserRepository;

public class SearchViewModel extends ViewModel implements UserRepository.OnInfoCallBack {
    private UserRepository repository;

    private MutableLiveData<User> userMutableLiveData;

    public SearchViewModel() {
        repository = UserRepository.getInstance();
        userMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<User> getUser() {
        String defaultName = "iray68";
        findUser(defaultName);
        return userMutableLiveData;
    }

    public void findUser(String accountName) {
        if (TextUtils.isEmpty(accountName)) {
            return;
        }

        repository.getUser(accountName, this);
    }

    @Override
    public void onInformationTaken(User user) {
        userMutableLiveData.postValue(user);
    }
}