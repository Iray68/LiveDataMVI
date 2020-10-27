package dev.yiray.qickmvivm.retrofit;

import dev.yiray.qickmvivm.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserInterface {
    @GET("users/{user}")
    Call<User> findUser(@Path("user") String user);
}
