package com.example.retrofit.API;

import com.example.retrofit.Entity.UserRespose;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {
    //@Get: indicates that the HTTP method used to send the request is GET
    //"api/users" is the API endpoint we want to send request to
    //This method will return the list of user base on number page
    @GET("api/users")
    Call<UserRespose> getUsers(@Query("page") int page);
    // Page la tham so duoc them vao URL duoi dang query parameter khi gui yeu cau GET.
    // Gia tri nay duoc cung cap khi goi getUser() trong UserAPIClient
}
