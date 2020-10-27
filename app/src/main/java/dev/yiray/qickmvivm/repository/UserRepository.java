package dev.yiray.qickmvivm.repository;

import dev.yiray.qickmvivm.model.User;
import dev.yiray.qickmvivm.retrofit.UserInterface;
import dev.yiray.qickmvivm.retrofit.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final UserInterface userInterface;
    private static UserRepository repository;

    public interface OnInfoCallBack {
        void onInformationTaken(User user);
    }

    public static UserRepository getInstance(){
        if (repository == null){
            repository = new UserRepository();
        }
        return repository;
    }

    public UserRepository(){
        userInterface = UserService.getInterface();
    }

    public void getUser(String userName, OnInfoCallBack callBack) {
        userInterface.findUser(userName).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                callBack.onInformationTaken(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callBack.onInformationTaken(null);
            }
        });
    }

}
