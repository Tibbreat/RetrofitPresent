package com.example.retrofit.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPIClient {
    private static final String BASE_URL = "https://reqres.in/"; //Địa chỉ cơ sở của API
    private Retrofit retrofit;
    private UserService userService;

    public UserAPIClient() {
        retrofit = new Retrofit.Builder() //Tạo 1 đối tượng retrofit.builder để cấu hình retrofit
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Chuyen doi JSON thành Java object
                .build(); // Kết thúc cấu hiình và tạo retrofit object

        userService = retrofit.create(UserService.class);
    }

    public UserService getUserService() {
        return userService;
    }
}
