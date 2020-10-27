package dev.yiray.qickmvivm.retrofit;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {
    private static final String BASE_URL = "https://api.github.com/";
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static UserInterface getInterface() {
        return retrofit.create(UserInterface.class);
    }
}
